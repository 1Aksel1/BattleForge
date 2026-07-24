package com.battleforge.backend.repository;

import com.battleforge.backend.model.Run;
import com.battleforge.backend.model.User;
import com.battleforge.backend.shared.enums.RunStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RunRepository extends JpaRepository<Run, Long> {

    boolean existsByHeroUserAndStatus(User user, RunStatus status);

    Optional<Run> findByHeroUserAndStatus(User user, RunStatus status);

}
