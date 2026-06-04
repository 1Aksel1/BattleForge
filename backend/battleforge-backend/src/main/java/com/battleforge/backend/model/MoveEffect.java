package com.battleforge.backend.model;

import com.battleforge.backend.shared.enums.EffectTarget;
import com.battleforge.backend.shared.enums.EffectType;
import com.battleforge.backend.shared.enums.StatType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveEffect {

    @Enumerated(EnumType.STRING)
    private EffectType effectType;

    @Enumerated(EnumType.STRING)
    private StatType stat;

    @Enumerated(EnumType.STRING)
    private EffectTarget target;

    private Double value;

    private Integer duration;
}
