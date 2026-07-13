package com.battleforge.backend.model;

import com.battleforge.backend.shared.enums.BattleStatus;
import com.battleforge.backend.shared.enums.BattleWinner;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hero_battle_state_id")
    private HeroBattleState hero;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "monster_battle_state_id")
    private MonsterBattleState monster;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    @Enumerated(EnumType.STRING)
    private BattleWinner winner;

    @ManyToOne
    @JoinColumn(name = "run_id")
    private Run run;

}
