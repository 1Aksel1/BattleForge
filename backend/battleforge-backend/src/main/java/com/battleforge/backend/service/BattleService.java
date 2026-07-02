package com.battleforge.backend.service;

import com.battleforge.backend.dto.BattleStateDto;
import com.battleforge.backend.dto.StartBattleRequest;
import com.battleforge.backend.exceptions.InvalidBattleStateException;
import com.battleforge.backend.exceptions.ResourceNotFoundException;
import com.battleforge.backend.mapper.BattleMapper;
import com.battleforge.backend.model.BattleState;
import com.battleforge.backend.model.HeroBattleState;
import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.MonsterBattleState;
import com.battleforge.backend.model.Move;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.repository.BattleStateRepository;
import com.battleforge.backend.repository.MonsterRepository;
import com.battleforge.backend.repository.MoveRepository;
import com.battleforge.backend.repository.RunRepository;
import com.battleforge.backend.shared.enums.BattleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BattleService {

    private final RunRepository runRepository;
    private final MonsterRepository monsterRepository;
    private final MoveRepository moveRepository;
    private final BattleStateRepository battleStateRepository;
    private final BattleMapper battleMapper;

    public BattleStateDto startBattle(StartBattleRequest request) {

        if (request.getEquippedMoveIds().size() != 4) {
            throw new InvalidBattleStateException("Exactly 4 moves must be equipped.");
        }

        Run run = runRepository.findById(request.getRunId())
                .orElseThrow(() -> new ResourceNotFoundException("Run not found with id: " + request.getRunId()));

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

}
