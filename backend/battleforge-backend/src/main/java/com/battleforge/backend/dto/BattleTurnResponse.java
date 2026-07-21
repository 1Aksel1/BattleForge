package com.battleforge.backend.dto;

import com.battleforge.backend.shared.enums.BattleWinner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleTurnResponse {

    private Long battleStateId;
    private HeroBattleStateDto heroAfterHeroMove;
    private MonsterBattleStateDto monsterAfterHeroMove;
    private HeroBattleStateDto heroAfterMonsterMove;
    private MonsterBattleStateDto monsterAfterMonsterMove;
    private String monsterMoveName;
    private Boolean battleOver;
    private BattleWinner winner;

}
