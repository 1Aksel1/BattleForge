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
public class StartBattleRequest {

    private Long runId;

    private Long monsterId;

    private List<Long> equippedMoveIds;

}
