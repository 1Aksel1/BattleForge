package com.battleforge.backend.service;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.mapper.RunMapper;
import com.battleforge.backend.model.Hero;
import com.battleforge.backend.model.Monster;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.repository.HeroRepository;
import com.battleforge.backend.repository.MonsterRepository;
import com.battleforge.backend.repository.RunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {

    private final HeroRepository heroRepository;
    private final MonsterRepository monsterRepository;
    private final RunRepository runRepository;
    private final RunMapper runMapper;

    public RunConfigurationResponse createRun() {

        List<Monster> monsters = monsterRepository.findAll();

        Hero hero = heroRepository.findAll().getFirst();

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

}
