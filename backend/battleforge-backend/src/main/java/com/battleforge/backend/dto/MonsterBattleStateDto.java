package com.battleforge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonsterBattleStateDto {

    private String name;

    private Double currentHp;

    private Double maxHp;

    private Double attack;

    private Double defense;

    private Double magic;

}
