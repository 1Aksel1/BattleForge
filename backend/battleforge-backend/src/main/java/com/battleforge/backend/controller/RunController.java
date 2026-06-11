package com.battleforge.backend.controller;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.service.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RunController {

    private final RunService runService;

    @GetMapping("/run")
    public ResponseEntity<RunConfigurationResponse> createRun() {
        return ResponseEntity.ok(runService.createRun());
    }

}
