package com.battleforge.backend.controller;

import com.battleforge.backend.dto.BattleResolveResponse;
import com.battleforge.backend.dto.BattleStateDto;
import com.battleforge.backend.dto.BattleTurnResponse;
import com.battleforge.backend.dto.PlayTurnRequest;
import com.battleforge.backend.dto.StartBattleRequest;
import com.battleforge.backend.model.User;
import com.battleforge.backend.service.BattleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping("/battle/start")
    public ResponseEntity<BattleStateDto> startBattle(@RequestBody StartBattleRequest request, HttpServletRequest httpRequest) {
        User user = (User) httpRequest.getAttribute("session");
        return ResponseEntity.ok(battleService.startBattle(request, user));
    }

    @PostMapping("/battle/turn")
    public ResponseEntity<BattleTurnResponse> playTurn(@RequestBody PlayTurnRequest request, HttpServletRequest httpRequest) {
        User user = (User) httpRequest.getAttribute("session");
        return ResponseEntity.ok(battleService.playTurn(request, user));
    }

    @PostMapping("/battle/resolve/{battleStateId}")
    public ResponseEntity<BattleResolveResponse> resolveBattle(@PathVariable Long battleStateId, HttpServletRequest httpRequest) {
        User user = (User) httpRequest.getAttribute("session");
        return ResponseEntity.ok(battleService.resolveBattle(battleStateId, user));
    }

}
