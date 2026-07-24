package com.battleforge.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer level;

    private Integer xp;

    private Double health;

    private Double attack;

    private Double defense;

    private Double magic;

    @ManyToMany
    @JoinTable(
        name = "hero_learned_moves",
        joinColumns = @JoinColumn(name = "hero_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    @Builder.Default
    private List<Move> learnedMoves = new ArrayList<>();

}
