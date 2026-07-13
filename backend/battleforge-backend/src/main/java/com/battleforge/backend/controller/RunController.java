package com.battleforge.backend.controller;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.service.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RunController {

    private final RunService runService;

    @PostMapping("/run")
    public ResponseEntity<RunConfigurationResponse> createRun() {
        return ResponseEntity.ok(runService.createRun());
    }

    @GetMapping("/run/{runId}")
    public ResponseEntity<RunConfigurationResponse> getRun(@PathVariable Long runId) {
        return ResponseEntity.ok(runService.getRun(runId));
    }

}
