package com.battleforge.backend.repository;

import com.battleforge.backend.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<Move, Long> {
}
