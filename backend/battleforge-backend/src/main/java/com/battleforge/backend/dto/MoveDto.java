package com.battleforge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveDto {

    private Long id;

    private String name;

    private String description;

    private MoveEffectDto primaryEffect;

    private MoveEffectDto secondaryEffect;

}
