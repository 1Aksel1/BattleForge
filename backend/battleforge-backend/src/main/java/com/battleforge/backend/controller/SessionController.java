package com.battleforge.backend.controller;

import com.battleforge.backend.dto.CreateSessionRequest;
import com.battleforge.backend.dto.CreateSessionResponse;
import com.battleforge.backend.dto.SessionStatusResponse;
import com.battleforge.backend.model.User;
import com.battleforge.backend.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/session")
    public ResponseEntity<CreateSessionResponse> createSession(@RequestBody CreateSessionRequest request) {
        String sessionId = sessionService.createSession(request.getUsername());
        return ResponseEntity.ok(CreateSessionResponse.builder().sessionId(sessionId).build());
    }

    @GetMapping("/session/status")
    public ResponseEntity<SessionStatusResponse> getSessionStatus(HttpServletRequest request) {
        User user = (User) request.getAttribute("session");
        return ResponseEntity.ok(sessionService.getSessionStatus(user));
    }

}
