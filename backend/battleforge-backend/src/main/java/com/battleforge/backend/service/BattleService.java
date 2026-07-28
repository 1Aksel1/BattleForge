package com.battleforge.backend.service;

import com.battleforge.backend.dto.*;
import com.battleforge.backend.exceptions.InvalidBattleStateException;
import com.battleforge.backend.exceptions.ResourceNotFoundException;
import com.battleforge.backend.mapper.BattleMapper;
import com.battleforge.backend.mapper.RunMapper;
import com.battleforge.backend.model.ActiveEffect;
import com.battleforge.backend.model.BattleFighter;
import com.battleforge.backend.model.BattleState;
import com.battleforge.backend.model.Hero;
import com.battleforge.backend.model.HeroBattleState;
import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.MonsterBattleState;
import com.battleforge.backend.model.Move;
import com.battleforge.backend.model.MoveEffect;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.model.User;
import com.battleforge.backend.repository.BattleStateRepository;
import com.battleforge.backend.repository.HeroRepository;
import com.battleforge.backend.repository.MonsterRepository;
import com.battleforge.backend.repository.MoveRepository;
import com.battleforge.backend.repository.RunRepository;
import com.battleforge.backend.shared.OwnershipValidator;
import com.battleforge.backend.shared.enums.BattleStatus;
import com.battleforge.backend.shared.enums.BattleWinner;
import com.battleforge.backend.shared.enums.EffectTarget;
import com.battleforge.backend.shared.enums.EffectType;
import com.battleforge.backend.shared.enums.StatType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final RunRepository runRepository;
    private final MonsterRepository monsterRepository;
    private final MoveRepository moveRepository;
    private final BattleStateRepository battleStateRepository;
    private final HeroRepository heroRepository;
    private final BattleMapper battleMapper;
    private final RunMapper runMapper;
    private final OwnershipValidator ownershipValidator;

    private final Random random = new Random();

    private static final Map<Integer, Integer> XP_TO_LEVEL_UP = Map.of(1, 100, 2, 200, 3, 350, 4, 500);
    private static final double LEVEL_UP_HEALTH = 20.0;
    private static final double LEVEL_UP_ATTACK = 3.0;
    private static final double LEVEL_UP_DEFENSE = 2.0;
    private static final double LEVEL_UP_MAGIC = 2.0;

    public void abandonBattle(Long battleStateId, User user) {

        BattleState battleState = battleStateRepository.findById(battleStateId)
                .orElseThrow(() -> new ResourceNotFoundException("BattleState not found with id: " + battleStateId));

        ownershipValidator.assertRunBelongsToUser(battleState.getRun(), user);

        if (battleState.getStatus() != BattleStatus.ACTIVE) {
            throw new InvalidBattleStateException("Battle is not active.");
        }

        battleState.setStatus(BattleStatus.COMPLETED);
        battleState.setWinner(BattleWinner.MONSTER);
        battleStateRepository.save(battleState);
    }

    public BattleStateDto startBattle(StartBattleRequest request, User user) {

        if (request.getEquippedMoveIds().size() != 4) {
            throw new InvalidBattleStateException("Exactly 4 moves must be equipped.");
        }

        Run run = runRepository.findById(request.getRunId())
                .orElseThrow(() -> new ResourceNotFoundException("Run not found with id: " + request.getRunId()));

        ownershipValidator.assertRunBelongsToUser(run, user);

        Monster monster = monsterRepository.findById(request.getMonsterId())
                .orElseThrow(() -> new ResourceNotFoundException("Monster not found with id: " + request.getMonsterId()));

        List<Monster> runMonsters = run.getMonsters();

        int monsterIndex = -1;

        for (int i = 0; i < runMonsters.size(); i++) {
            if (runMonsters.get(i).getId().equals(monster.getId())) {
                monsterIndex = i;
                break;
            }
        }

        if (monsterIndex == -1) {
            throw new InvalidBattleStateException("Monster does not belong to this run.");
        }

        if (monsterIndex > run.getCurrentMonsterIndex()) {
            throw new InvalidBattleStateException("Monster is not yet available at the current run progress.");
        }

        Set<Long> learnedMoveIds = run.getHero().getLearnedMoves().stream()
                .map(Move::getId)
                .collect(Collectors.toSet());

        for (Long moveId : request.getEquippedMoveIds()) {
            if (!learnedMoveIds.contains(moveId)) {
                throw new InvalidBattleStateException("Move with id " + moveId + " is not in the hero's learned moves.");
            }
        }

        if (battleStateRepository.existsByRunAndStatus(run, BattleStatus.ACTIVE)) {
            throw new InvalidBattleStateException("An active battle already exists for this run.");
        }

        List<Move> equippedMoves = moveRepository.findAllById(request.getEquippedMoveIds());

        HeroBattleState heroBattleState = HeroBattleState.builder()
                .currentHp(run.getHero().getHealth())
                .maxHp(run.getHero().getHealth())
                .attack(run.getHero().getAttack())
                .defense(run.getHero().getDefense())
                .magic(run.getHero().getMagic())
                .equippedMoves(new ArrayList<>(equippedMoves))
                .build();

        MonsterBattleState monsterBattleState = MonsterBattleState.builder()
                .name(monster.getName())
                .currentHp(monster.getHealth())
                .maxHp(monster.getHealth())
                .attack(monster.getAttack())
                .defense(monster.getDefense())
                .magic(monster.getMagic())
                .moves(new ArrayList<>(monster.getMoves()))
                .build();

        BattleState battleState = BattleState.builder()
                .status(BattleStatus.ACTIVE)
                .run(run)
                .hero(heroBattleState)
                .monster(monsterBattleState)
                .build();

        BattleState saved = battleStateRepository.save(battleState);

        return BattleStateDto.builder()
                .battleStateId(saved.getId())
                .hero(battleMapper.toHeroBattleStateDto(saved.getHero()))
                .monster(battleMapper.toMonsterBattleStateDto(saved.getMonster()))
                .build();

    }

    public BattleTurnResponse playTurn(PlayTurnRequest request, User user) {

        BattleState battleState = battleStateRepository.findById(request.getBattleStateId())
                .orElseThrow(() -> new ResourceNotFoundException("BattleState not found with id: " + request.getBattleStateId()));

        ownershipValidator.assertRunBelongsToUser(battleState.getRun(), user);

        if (battleState.getStatus() != BattleStatus.ACTIVE) {
            throw new InvalidBattleStateException("Battle is not active.");
        }

        HeroBattleState hero = battleState.getHero();
        MonsterBattleState monster = battleState.getMonster();

        Move heroMove = hero.getEquippedMoves().stream()
                .filter(m -> m.getId().equals(request.getMoveId()))
                .findFirst()
                .orElseThrow(() -> new InvalidBattleStateException("Move is not equipped."));

        applyMoveEffects(heroMove, hero, monster);
        tickActiveEffects(hero);

        HeroBattleStateDto heroAfterHeroMove = battleMapper.toHeroBattleStateDto(hero);
        MonsterBattleStateDto monsterAfterHeroMove = battleMapper.toMonsterBattleStateDto(monster);

        if (monster.getCurrentHp() <= 0) {

            monster.setCurrentHp(0.0);
            monsterAfterHeroMove.setCurrentHp(0.0);
            battleState.setStatus(BattleStatus.COMPLETED);
            battleState.setWinner(BattleWinner.HERO);
            BattleState saved = battleStateRepository.save(battleState);

            return BattleTurnResponse.builder()
                    .battleStateId(saved.getId())
                    .heroAfterHeroMove(heroAfterHeroMove)
                    .monsterAfterHeroMove(monsterAfterHeroMove)
                    .heroAfterMonsterMove(null)
                    .monsterAfterMonsterMove(null)
                    .monsterMoveName(null)
                    .battleOver(true)
                    .winner(BattleWinner.HERO)
                    .build();

        }

        List<Move> monsterMoves = monster.getMoves();
        Move monsterMove = monsterMoves.get(this.random.nextInt(monsterMoves.size()));

        applyMoveEffects(monsterMove, monster, hero);
        tickActiveEffects(monster);

        HeroBattleStateDto heroAfterMonsterMove = battleMapper.toHeroBattleStateDto(hero);
        MonsterBattleStateDto monsterAfterMonsterMove = battleMapper.toMonsterBattleStateDto(monster);

        if (hero.getCurrentHp() <= 0) {

            hero.setCurrentHp(0.0);
            heroAfterMonsterMove.setCurrentHp(0.0);
            battleState.setStatus(BattleStatus.COMPLETED);
            battleState.setWinner(BattleWinner.MONSTER);
            BattleState saved = battleStateRepository.save(battleState);

            return BattleTurnResponse.builder()
                    .battleStateId(saved.getId())
                    .heroAfterHeroMove(heroAfterHeroMove)
                    .monsterAfterHeroMove(monsterAfterHeroMove)
                    .heroAfterMonsterMove(heroAfterMonsterMove)
                    .monsterAfterMonsterMove(monsterAfterMonsterMove)
                    .monsterMoveName(monsterMove.getName())
                    .battleOver(true)
                    .winner(BattleWinner.MONSTER)
                    .build();

        }

        BattleState saved = battleStateRepository.save(battleState);

        return BattleTurnResponse.builder()
                .battleStateId(saved.getId())
                .heroAfterHeroMove(heroAfterHeroMove)
                .monsterAfterHeroMove(monsterAfterHeroMove)
                .heroAfterMonsterMove(heroAfterMonsterMove)
                .monsterAfterMonsterMove(monsterAfterMonsterMove)
                .monsterMoveName(monsterMove.getName())
                .battleOver(false)
                .winner(null)
                .build();

    }

    public BattleResolveResponse resolveBattle(Long battleStateId, User user) {

        BattleState battleState = battleStateRepository.findById(battleStateId)
                .orElseThrow(() -> new ResourceNotFoundException("BattleState not found with id: " + battleStateId));

        ownershipValidator.assertRunBelongsToUser(battleState.getRun(), user);

        if (battleState.getStatus() == BattleStatus.RESOLVED) {
            throw new InvalidBattleStateException("Battle has already been resolved.");
        }

        if (battleState.getStatus() != BattleStatus.COMPLETED) {
            throw new InvalidBattleStateException("Battle is not completed yet.");
        }

        if (battleState.getWinner() != BattleWinner.HERO) {
            throw new InvalidBattleStateException("Hero did not win this battle.");
        }

        Run run = battleState.getRun();

        Monster monster = monsterRepository.findByName(battleState.getMonster().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Monster not found with name: " + battleState.getMonster().getName()));

        int xpGained = monster.getXpReward();

        Hero hero = run.getHero();

        boolean leveledUp = applyXpAndLevelUp(hero, xpGained);
        int newLevel = hero.getLevel();

        Move learnedMove = selectMoveToLearn(monster, hero);

        if (learnedMove != null) {
            hero.getLearnedMoves().add(learnedMove);
        }
        heroRepository.save(hero);

        MoveDto learnedMoveDto = learnedMove != null ? runMapper.toMoveDto(learnedMove) : null;

        List<Monster> runMonsters = run.getMonsters();

        int defeatedIndex = -1;

        for (int i = 0; i < runMonsters.size(); i++) {
            if (runMonsters.get(i).getId().equals(monster.getId())) {
                defeatedIndex = i;
                break;
            }
        }

        if (defeatedIndex == run.getCurrentMonsterIndex()) {
            run.setCurrentMonsterIndex(run.getCurrentMonsterIndex() + 1);
            runRepository.save(run);
        }

        boolean runComplete = run.getCurrentMonsterIndex() >= 5;

        battleState.setStatus(BattleStatus.RESOLVED);
        battleStateRepository.save(battleState);

        return BattleResolveResponse.builder()
                .xpGained(xpGained)
                .leveledUp(leveledUp)
                .newLevel(newLevel)
                .learnedMove(learnedMoveDto)
                .runComplete(runComplete)
                .build();

    }

    private boolean applyXpAndLevelUp(Hero hero, int xpGained) {

        hero.setXp(hero.getXp() + xpGained);

        if (hero.getLevel() >= 5) {
            return false;
        }

        Integer threshold = XP_TO_LEVEL_UP.get(hero.getLevel());

        if (hero.getXp() >= threshold) {

            hero.setLevel(hero.getLevel() + 1);
            hero.setXp(hero.getXp() - threshold);
            hero.setHealth(hero.getHealth() + LEVEL_UP_HEALTH);
            hero.setAttack(hero.getAttack() + LEVEL_UP_ATTACK);
            hero.setDefense(hero.getDefense() + LEVEL_UP_DEFENSE);
            hero.setMagic(hero.getMagic() + LEVEL_UP_MAGIC);
            return true;

        }

        return false;

    }

    private Move selectMoveToLearn(Monster monster, Hero hero) {

        Set<Long> learnedIds = hero.getLearnedMoves().stream()
                .map(Move::getId)
                .collect(Collectors.toSet());

        List<Move> candidates = monster.getMoves().stream()
                .filter(m -> !learnedIds.contains(m.getId()))
                .toList();

        if (candidates.isEmpty()) {
            return null;
        }

        return candidates.get(this.random.nextInt(candidates.size()));
    }

    private void applyMoveEffects(Move move, BattleFighter attacker, BattleFighter opponent) {

        applyEffect(move.getPrimaryEffect(), attacker, opponent);

        if (move.getSecondaryEffect() != null) {
            applyEffect(move.getSecondaryEffect(), attacker, opponent);
        }

    }

    private void applyEffect(MoveEffect effect, BattleFighter caster, BattleFighter opponent) {

        BattleFighter actualTarget = effect.getTarget() == EffectTarget.SELF ? caster : opponent;

        switch (effect.getEffectType()) {

            case DAMAGE -> {
                double damage = switch (effect.getStat()) {
                    case ATTACK -> Math.max(1.0, effect.getValue() * caster.getAttack() - actualTarget.getDefense());
                    case MAGIC  -> Math.max(1.0, effect.getValue() * caster.getMagic());
                    case HP     -> effect.getValue() * caster.getCurrentHp();
                    default     -> 1.0;
                };
                actualTarget.setCurrentHp(actualTarget.getCurrentHp() - damage);
            }

            case HEAL -> {
                double heal = effect.getValue() * caster.getMagic();
                actualTarget.setCurrentHp(Math.min(actualTarget.getMaxHp(), actualTarget.getCurrentHp() + heal));
            }

            case BUFF -> {
                applyStatChange(actualTarget, effect.getStat(), effect.getValue());
                boolean selfApplied = effect.getTarget() == EffectTarget.SELF;
                actualTarget.getActiveEffects().add(ActiveEffect.builder()
                        .stat(effect.getStat())
                        .value(effect.getValue())
                        .justSelfApplied(selfApplied)
                        .turnsRemaining(effect.getDuration())
                        .build());
            }

            case DEBUFF -> {
                applyStatChange(actualTarget, effect.getStat(), -effect.getValue());
                actualTarget.getActiveEffects().add(ActiveEffect.builder()
                        .stat(effect.getStat())
                        .value(-effect.getValue())
                        .turnsRemaining(effect.getDuration())
                        .build());
            }

        }
    }

    private void tickActiveEffects(BattleFighter fighter) {

        Iterator<ActiveEffect> it = fighter.getActiveEffects().iterator();

        while (it.hasNext()) {

            ActiveEffect effect = it.next();

            if(effect.isJustSelfApplied()) {
                effect.setJustSelfApplied(false);
                continue;
            }

            effect.setTurnsRemaining(effect.getTurnsRemaining() - 1);

            if (effect.getTurnsRemaining() == 0) {
                applyStatChange(fighter, effect.getStat(), -effect.getValue());
                it.remove();
            }

        }

    }

    private void applyStatChange(BattleFighter fighter, StatType stat, double delta) {
        switch (stat) {
            case ATTACK  -> fighter.setAttack(fighter.getAttack() + delta);
            case DEFENSE -> fighter.setDefense(fighter.getDefense() + delta);
            case MAGIC   -> fighter.setMagic(fighter.getMagic() + delta);
        }
    }

}
