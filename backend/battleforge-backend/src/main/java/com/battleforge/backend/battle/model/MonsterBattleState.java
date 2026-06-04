package com.battleforge.backend.battle.model;

import com.battleforge.backend.move.model.Move;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonsterBattleState {

    private Double currentHp;

    private Double attack;

    private Double defense;

    private Double magic;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "monster_battle_active_effects",
        joinColumns = @JoinColumn(name = "battle_state_id"),
        inverseJoinColumns = @JoinColumn(name = "active_effect_id")
    )
    @Builder.Default
    private List<ActiveEffect> activeEffects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "monster_battle_moves",
        joinColumns = @JoinColumn(name = "battle_state_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    @Builder.Default
    private List<Move> moves = new ArrayList<>();
}
