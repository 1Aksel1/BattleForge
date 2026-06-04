package com.battleforge.backend.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "battle_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currentHp", column = @Column(name = "hero_current_hp")),
        @AttributeOverride(name = "attack",    column = @Column(name = "hero_attack")),
        @AttributeOverride(name = "defense",   column = @Column(name = "hero_defense")),
        @AttributeOverride(name = "magic",     column = @Column(name = "hero_magic"))
    })
    private HeroBattleState hero;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "currentHp", column = @Column(name = "monster_current_hp")),
        @AttributeOverride(name = "attack",    column = @Column(name = "monster_attack")),
        @AttributeOverride(name = "defense",   column = @Column(name = "monster_defense")),
        @AttributeOverride(name = "magic",     column = @Column(name = "monster_magic"))
    })
    private MonsterBattleState monster;
}
