package com.battleforge.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monster_battle_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonsterBattleState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double currentHp;

    private Double maxHp;

    private Double attack;

    private Double defense;

    private Double magic;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "monster_battle_active_effects",
        joinColumns = @JoinColumn(name = "monster_battle_state_id"),
        inverseJoinColumns = @JoinColumn(name = "active_effect_id")
    )
    @Builder.Default
    private List<ActiveEffect> activeEffects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "monster_battle_moves",
        joinColumns = @JoinColumn(name = "monster_battle_state_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    @Builder.Default
    private List<Move> moves = new ArrayList<>();
}
