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
public class HeroRunDto {

    private Long id;

    private String username;

    private Integer level;

    private Integer xp;

    private Double health;

    private Double attack;

    private Double defense;

    private Double magic;

    private List<MoveDto> learnedMoves;
}
