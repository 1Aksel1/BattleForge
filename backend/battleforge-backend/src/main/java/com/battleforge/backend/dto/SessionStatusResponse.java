package com.battleforge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionStatusResponse {

    private boolean hasActiveRun;
    private Long runId;
    private boolean hasActiveBattle;
    private Long battleStateId;

}
