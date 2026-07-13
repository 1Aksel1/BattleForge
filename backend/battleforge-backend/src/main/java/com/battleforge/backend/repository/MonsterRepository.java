package com.battleforge.backend.repository;

import com.battleforge.backend.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonsterRepository extends JpaRepository<Monster, Long> {

    Optional<Monster> findByName(String name);

}
