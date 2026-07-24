package com.battleforge.backend.service;

import com.battleforge.backend.dto.SessionStatusResponse;
import com.battleforge.backend.exceptions.UsernameAlreadyTakenException;
import com.battleforge.backend.model.BattleState;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.model.User;
import com.battleforge.backend.repository.BattleStateRepository;
import com.battleforge.backend.repository.RunRepository;
import com.battleforge.backend.repository.UserRepository;
import com.battleforge.backend.shared.enums.BattleStatus;
import com.battleforge.backend.shared.enums.RunStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;
    private final RunRepository runRepository;
    private final BattleStateRepository battleStateRepository;

    public String createSession(String username) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyTakenException("Username already taken.");
        }

        User user = userRepository.save(User.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .createdAt(LocalDateTime.now())
                .build());

        return user.getId();

    }

    public SessionStatusResponse getSessionStatus(User user) {

        Optional<Run> activeRun = runRepository.findByHeroUserAndStatus(user, RunStatus.ACTIVE);

        if (activeRun.isEmpty()) {
            return SessionStatusResponse.builder()
                    .hasActiveRun(false)
                    .runId(null)
                    .hasActiveBattle(false)
                    .battleStateId(null)
                    .build();
        }

        Run run = activeRun.get();

        Optional<BattleState> activeBattle = battleStateRepository.findByRunAndStatus(run, BattleStatus.ACTIVE);

        return SessionStatusResponse.builder()
                .hasActiveRun(true)
                .runId(run.getId())
                .hasActiveBattle(activeBattle.isPresent())
                .battleStateId(activeBattle.map(BattleState::getId).orElse(null))
                .build();

    }

}
