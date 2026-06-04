package com.battleforge.backend.run;

import com.battleforge.backend.hero.Hero;
import com.battleforge.backend.monster.Monster;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "run")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @ManyToMany
    @JoinTable(
        name = "run_monsters",
        joinColumns = @JoinColumn(name = "run_id"),
        inverseJoinColumns = @JoinColumn(name = "monster_id")
    )
    @Builder.Default
    private List<Monster> monsters = new ArrayList<>();

    private Integer currentMonsterIndex;
}
