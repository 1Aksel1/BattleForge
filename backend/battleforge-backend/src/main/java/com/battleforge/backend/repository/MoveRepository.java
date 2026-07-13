package com.battleforge.backend.repository;

import com.battleforge.backend.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Long> {

    Optional<Move> findByName(String name);

}
