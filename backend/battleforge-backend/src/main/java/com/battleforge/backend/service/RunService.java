package com.battleforge.backend.service;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.exceptions.ResourceNotFoundException;
import com.battleforge.backend.mapper.RunMapper;
import com.battleforge.backend.model.Hero;
import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.Move;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.repository.HeroRepository;
import com.battleforge.backend.repository.MonsterRepository;
import com.battleforge.backend.repository.MoveRepository;
import com.battleforge.backend.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {

    private final HeroRepository heroRepository;
    private final MoveRepository moveRepository;
    private final MonsterRepository monsterRepository;
    private final RunRepository runRepository;
    private final RunMapper runMapper;

    public RunConfigurationResponse createRun() {

        List<Monster> monsters = monsterRepository.findAll();

        Move slash = moveRepository.findByName("Slash")
                .orElseThrow(() -> new ResourceNotFoundException("Move not found: Slash"));
        Move shieldUp = moveRepository.findByName("Shield Up")
                .orElseThrow(() -> new ResourceNotFoundException("Move not found: Shield Up"));
        Move battleCry = moveRepository.findByName("Battle Cry")
                .orElseThrow(() -> new ResourceNotFoundException("Move not found: Battle Cry"));
        Move secondWind = moveRepository.findByName("Second Wind")
                .orElseThrow(() -> new ResourceNotFoundException("Move not found: Second Wind"));

        Hero hero = heroRepository.save(Hero.builder()
                .username("Knight")
                .level(1)
                .xp(0)
                .health(100.0)
                .attack(15.0)
                .defense(10.0)
                .magic(10.0)
                .learnedMoves(new ArrayList<>(List.of(slash, shieldUp, battleCry, secondWind)))
                .build());

        Run run = runRepository.save(Run.builder()
                .hero(hero)
                .monsters(monsters)
                .currentMonsterIndex(0)
                .build());

        return RunConfigurationResponse.builder()
                .runId(run.getId())
                .currentMonsterIndex(run.getCurrentMonsterIndex())
                .hero(runMapper.toHeroRunDto(hero))
                .monsters(monsters.stream().map(runMapper::toMonsterRunDto).toList())
                .build();
    }

    public RunConfigurationResponse getRun(Long runId) {

        Run run = runRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found with id: " + runId));

        return RunConfigurationResponse.builder()
                .runId(run.getId())
                .currentMonsterIndex(run.getCurrentMonsterIndex())
                .hero(runMapper.toHeroRunDto(run.getHero()))
                .monsters(run.getMonsters().stream().map(runMapper::toMonsterRunDto).toList())
                .build();
    }

}
