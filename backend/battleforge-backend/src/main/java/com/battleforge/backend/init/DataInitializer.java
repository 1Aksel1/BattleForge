package com.battleforge.backend.init;

import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.Move;
import com.battleforge.backend.model.MoveEffect;
import com.battleforge.backend.repository.MonsterRepository;
import com.battleforge.backend.repository.MoveRepository;
import com.battleforge.backend.shared.enums.EffectTarget;
import com.battleforge.backend.shared.enums.EffectType;
import com.battleforge.backend.shared.enums.StatType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MoveRepository moveRepository;
    private final MonsterRepository monsterRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // ── Move value

        // Light damage:   0.5–1.0 × stat   (Drain Life, Mana Drain, Web Throw, Dirty Kick)
        // Moderate damage: 1.5 × stat      (Slash, Bite, Rusty Blade, Firebolt)
        // Heavy damage:   2.0 × stat       (Pounce, Headbutt)
        // Heal:           1.0 × Magic      (Second Wind)
        // Buff/Debuff:    +/- 5.0 flat     (applied to stat for 2 turns)


        // ── Knight moves

        Move slash = Move.builder()
                .name("Slash")
                .description("A powerful sword strike that deals moderate physical damage, scaling with Attack reduced by Defense.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move shieldUp = Move.builder()
                .name("Shield Up")
                .description("Raise your shield to boost Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move battleCry = Move.builder()
                .name("Battle Cry")
                .description("Let out a war cry that boosts Attack for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move secondWind = Move.builder()
                .name("Second Wind")
                .description("Draw on inner reserves to heal a moderate amount, scaling with Magic.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.HEAL)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.SELF)
                        .value(1.0)
                        .duration(null)
                        .build())
                .build();

        // ── Witch moves

        Move shadowBolt = Move.builder()
                .name("Shadow Bolt")
                .description("Hurl a bolt of dark energy dealing heavy magic damage, scaling with Magic.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move drainLife = Move.builder()
                .name("Drain Life")
                .description("Drain the target's life force, dealing light magic damage and healing yourself for the same amount.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(0.5)
                        .duration(null)
                        .build())
                .secondaryEffect(MoveEffect.builder()
                        .effectType(EffectType.HEAL)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.SELF)
                        .value(0.5)
                        .duration(null)
                        .build())
                .build();

        Move curse = Move.builder()
                .name("Curse")
                .description("Place a curse on the target, reducing their Attack for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DEBUFF)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move darkPact = Move.builder()
                .name("Dark Pact")
                .description("Make a dark bargain to boost Magic for 2 turns at the cost of 15% of your own HP.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .secondaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.HP)
                        .target(EffectTarget.SELF)
                        .value(0.15)
                        .duration(null)
                        .build())
                .build();

        // ── Giant Spider moves

        Move bite = Move.builder()
                .name("Bite")
                .description("Sink venomous fangs into the target for moderate physical damage.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move webThrow = Move.builder()
                .name("Web Throw")
                .description("Fling sticky webs dealing light physical damage and reducing the target's Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.0)
                        .duration(null)
                        .build())
                .secondaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DEBUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.OPPONENT)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move pounce = Move.builder()
                .name("Pounce")
                .description("Leap at the target with full force dealing heavy physical damage.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(2.0)
                        .duration(null)
                        .build())
                .build();

        Move skitter = Move.builder()
                .name("Skitter")
                .description("Move erratically to boost Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        // ── Dragon moves

        Move flameBreath = Move.builder()
                .name("Flame Breath")
                .description("Exhale a torrent of fire dealing heavy magic damage, scaling with Magic.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move clawSwipe = Move.builder()
                .name("Claw Swipe")
                .description("Swipe with razor-sharp claws for moderate physical damage.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move intimidate = Move.builder()
                .name("Intimidate")
                .description("Let out a terrifying roar that reduces the target's Attack for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DEBUFF)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move dragonScales = Move.builder()
                .name("Dragon Scales")
                .description("Harden your scales to boost Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        // ── Goblin Warrior moves

        Move rustyBlade = Move.builder()
                .name("Rusty Blade")
                .description("A wild swing with a rusty sword dealing moderate physical damage.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.5)
                        .duration(null)
                        .build())
                .build();

        Move dirtyKick = Move.builder()
                .name("Dirty Kick")
                .description("A low kick that deals light physical damage and lowers the target's Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(1.0)
                        .duration(null)
                        .build())
                .secondaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DEBUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.OPPONENT)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move frenzy = Move.builder()
                .name("Frenzy")
                .description("Enter a berserker rage that boosts Attack for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move headbutt = Move.builder()
                .name("Headbutt")
                .description("Charge forward and slam your head into the target for heavy physical damage.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.ATTACK)
                        .target(EffectTarget.OPPONENT)
                        .value(2.0)
                        .duration(null)
                        .build())
                .build();

        // ── Goblin Mage moves

        Move firebolt = Move.builder()
                .name("Firebolt")
                .description("Launch a bolt of fire dealing moderate magic damage, scaling with Magic.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(1.0)
                        .duration(null)
                        .build())
                .build();

        Move arcaneSurge = Move.builder()
                .name("Arcane Surge")
                .description("Channel arcane energy to boost Magic for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move manaDrain = Move.builder()
                .name("Mana Drain")
                .description("Siphon magical energy dealing light magic damage and reducing the target's Magic for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DAMAGE)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(0.5)
                        .duration(null)
                        .build())
                .secondaryEffect(MoveEffect.builder()
                        .effectType(EffectType.DEBUFF)
                        .stat(StatType.MAGIC)
                        .target(EffectTarget.OPPONENT)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        Move hexShield = Move.builder()
                .name("Hex Shield")
                .description("Weave a magical barrier that boosts Defense for 2 turns.")
                .primaryEffect(MoveEffect.builder()
                        .effectType(EffectType.BUFF)
                        .stat(StatType.DEFENSE)
                        .target(EffectTarget.SELF)
                        .value(5.0)
                        .duration(2)
                        .build())
                .build();

        moveRepository.saveAll(List.of(
                slash, shieldUp, battleCry, secondWind,
                shadowBolt, drainLife, curse, darkPact,
                bite, webThrow, pounce, skitter,
                flameBreath, clawSwipe, intimidate, dragonScales,
                rustyBlade, dirtyKick, frenzy, headbutt,
                firebolt, arcaneSurge, manaDrain, hexShield
        ));

        // ── Monsters (easiest → hardest) ──────────────────────────────────────

        // Baseline: hero health ~100, attack ~15, defense ~10, magic ~10
        // Physical damage = value * attackStat - targetDefenseStat
        // Magic damage    = value * magicStat
        // Buff/Debuff     = flat ±value added to stat for duration turns

        Monster goblinWarrior = Monster.builder()
                .name("Goblin Warrior")
                .imageUrl("")
                .health(60.0)
                .attack(12.0)
                .defense(6.0)
                .magic(3.0)
                .xpReward(30)
                .moves(List.of(rustyBlade, dirtyKick, frenzy, headbutt))
                .build();

        // Rusty Blade vs hero: 1.5*12 - 10 = 8 dmg/hit; hero Slash vs goblin: 1.5*15 - 6 = 16.5 dmg/hit
        // Goblin falls in ~4 hits; hero survives ~12 hits → beatable at Level 1

        Monster giantSpider = Monster.builder()
                .name("Giant Spider")
                .imageUrl("")
                .health(80.0)
                .attack(14.0)
                .defense(8.0)
                .magic(4.0)
                .xpReward(50)
                .moves(List.of(bite, webThrow, pounce, skitter))
                .build();

        // Bite vs hero: 1.5*14 - 10 = 11 dmg/hit; hero Slash vs spider: 1.5*15 - 8 = 14.5 dmg/hit
        // Spider falls in ~6 hits; hero survives ~9 hits → tighter than Goblin, still Level 1-2

        Monster goblinMage = Monster.builder()
                .name("Goblin Mage")
                .imageUrl("")
                .health(70.0)
                .attack(8.0)
                .defense(6.0)
                .magic(14.0)
                .xpReward(70)
                .moves(List.of(firebolt, arcaneSurge, manaDrain, hexShield))
                .build();

        // Firebolt vs hero: 1.0*14 = 14 magic dmg (ignores defense); hero Slash vs mage: 1.5*15 - 6 = 16.5 dmg/hit
        // Mage falls in ~5 hits; hero survives ~7 hits → requires Level 2

        Monster witch = Monster.builder()
                .name("Witch")
                .imageUrl("")
                .health(90.0)
                .attack(10.0)
                .defense(8.0)
                .magic(18.0)
                .xpReward(100)
                .moves(List.of(shadowBolt, drainLife, curse, darkPact))
                .build();

        // Shadow Bolt vs hero: 1.5*18 = 27 magic dmg/hit; hero Slash vs witch: 1.5*15 - 8 = 14.5 dmg/hit
        // Witch falls in ~7 hits; hero survives ~4 hits → requires Level 3-4 and good use of moves

        Monster dragon = Monster.builder()
                .name("Dragon")
                .imageUrl("")
                .health(200.0)
                .attack(25.0)
                .defense(15.0)
                .magic(20.0)
                .xpReward(200)
                .moves(List.of(flameBreath, clawSwipe, intimidate, dragonScales))
                .build();

        // Flame Breath vs hero: 1.5*20 = 30 magic dmg/hit; Claw Swipe: 1.5*25 - 10 = 27.5 dmg/hit
        // hero Slash vs dragon (L1): 1.5*15 - 15 = 7.5 dmg/hit → requires several level-ups + strategy

        monsterRepository.saveAll(List.of(goblinWarrior, giantSpider, goblinMage, witch, dragon));
    }

}
