package com.battleforge.backend.dto;

import lombok.*;

@Getter
@Setter
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
