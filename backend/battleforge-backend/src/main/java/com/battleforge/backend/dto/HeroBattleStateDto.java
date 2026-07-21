package com.battleforge.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
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
