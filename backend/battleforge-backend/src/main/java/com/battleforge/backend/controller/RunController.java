package com.battleforge.backend.controller;

import com.battleforge.backend.dto.RunConfigurationResponse;
import com.battleforge.backend.service.RunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
