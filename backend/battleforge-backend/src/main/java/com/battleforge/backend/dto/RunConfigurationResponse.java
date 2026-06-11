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
public class RunConfigurationResponse {

    private Long runId;

    private Integer currentMonsterIndex;

    private HeroRunDto hero;

    private List<MonsterRunDto> monsters;

}
