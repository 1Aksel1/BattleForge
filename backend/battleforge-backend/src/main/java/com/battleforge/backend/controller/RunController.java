package com.battleforge.backend.controller;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.model.User;
import com.battleforge.backend.service.RunService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RunController {

    private final RunService runService;

    @PostMapping("/run")
    public ResponseEntity<RunConfigurationResponse> createRun(HttpServletRequest request) {
        User user = (User) request.getAttribute("session");
        return ResponseEntity.ok(runService.createRun(user));
    }

    @GetMapping("/run/{runId}")
    public ResponseEntity<RunConfigurationResponse> getRun(@PathVariable Long runId, HttpServletRequest request) {
        User user = (User) request.getAttribute("session");
        return ResponseEntity.ok(runService.getRun(runId, user));
    }

    @PutMapping("/run/{runId}/abandon")
    public ResponseEntity<Void> abandonRun(@PathVariable Long runId, HttpServletRequest request) {
        User user = (User) request.getAttribute("session");
        runService.abandonRun(runId, user);
        return ResponseEntity.ok().build();
    }

}
