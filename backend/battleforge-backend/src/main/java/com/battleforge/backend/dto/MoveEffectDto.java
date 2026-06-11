package com.battleforge.backend.dto;

import com.battleforge.backend.shared.enums.EffectTarget;
import com.battleforge.backend.shared.enums.EffectType;
import com.battleforge.backend.shared.enums.StatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveEffectDto {

    private EffectType effectType;

    private StatType stat;

    private EffectTarget target;

    private Double value;

    private Integer duration;

}
