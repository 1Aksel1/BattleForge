package com.battleforge.backend.repository;

import com.battleforge.backend.model.BattleState;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.shared.enums.BattleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BattleStateRepository extends JpaRepository<BattleState, Long> {

    boolean existsByRunAndStatus(Run run, BattleStatus status);

    Optional<BattleState> findByRunAndStatus(Run run, BattleStatus status);

}
