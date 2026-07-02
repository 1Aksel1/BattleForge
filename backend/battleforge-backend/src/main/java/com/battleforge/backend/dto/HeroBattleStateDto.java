package com.battleforge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeroBattleStateDto {

    private Double currentHp;

    private Double maxHp;

    private Double attack;

    private Double defense;

    private Double magic;

    private List<MoveDto> equippedMoves;

}
