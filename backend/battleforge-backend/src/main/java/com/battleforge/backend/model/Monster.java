package com.battleforge.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    private Double health;

    private Double attack;

    private Double defense;

    private Double magic;

    private Integer xpReward;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "monster_moves",
        joinColumns = @JoinColumn(name = "monster_id"),
        inverseJoinColumns = @JoinColumn(name = "move_id")
    )
    @Builder.Default
    private List<Move> moves = new ArrayList<>();
}
