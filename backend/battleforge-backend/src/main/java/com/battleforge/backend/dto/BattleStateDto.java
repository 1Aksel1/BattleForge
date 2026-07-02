package com.battleforge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattleStateDto {

    private Long battleStateId;

    private HeroBattleStateDto hero;

    private MonsterBattleStateDto monster;

}
