package com.battleforge.backend.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "move")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "effectType", column = @Column(name = "primary_effect_type")),
        @AttributeOverride(name = "stat",        column = @Column(name = "primary_effect_stat")),
        @AttributeOverride(name = "target",      column = @Column(name = "primary_effect_target")),
        @AttributeOverride(name = "value",       column = @Column(name = "primary_effect_value")),
        @AttributeOverride(name = "duration",    column = @Column(name = "primary_effect_duration"))
    })
    private MoveEffect primaryEffect;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "effectType", column = @Column(name = "secondary_effect_type",     nullable = true)),
        @AttributeOverride(name = "stat",        column = @Column(name = "secondary_effect_stat",     nullable = true)),
        @AttributeOverride(name = "target",      column = @Column(name = "secondary_effect_target",   nullable = true)),
        @AttributeOverride(name = "value",       column = @Column(name = "secondary_effect_value",    nullable = true)),
        @AttributeOverride(name = "duration",    column = @Column(name = "secondary_effect_duration", nullable = true))
    })
    private MoveEffect secondaryEffect;
}
