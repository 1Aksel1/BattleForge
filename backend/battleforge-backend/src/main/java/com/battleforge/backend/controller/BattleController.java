package com.battleforge.backend.controller;

import com.battleforge.backend.dto.BattleStateDto;
import com.battleforge.backend.dto.BattleTurnResponse;
import com.battleforge.backend.dto.PlayTurnRequest;
import com.battleforge.backend.dto.StartBattleRequest;
import com.battleforge.backend.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping("/battle/start")
    public ResponseEntity<BattleStateDto> startBattle(@RequestBody StartBattleRequest request) {
        return ResponseEntity.ok(battleService.startBattle(request));
    }

    @PostMapping("/battle/turn")
    public ResponseEntity<BattleTurnResponse> playTurn(@RequestBody PlayTurnRequest request) {
        return ResponseEntity.ok(battleService.playTurn(request));
    }

}
