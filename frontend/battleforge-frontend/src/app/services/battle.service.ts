import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { BattleStateDto, BattleTurnResponse, PlayTurnRequest, StartBattleRequest } from '../models/battle.model';

@Injectable({ providedIn: 'root' })
export class BattleService {

  private http = inject(HttpClient);
  currentBattle: BattleStateDto | null = null;

  startBattle(request: StartBattleRequest): Observable<BattleStateDto> {
    return this.http.post<BattleStateDto>('http://localhost:8080/api/battle/start', request).pipe(
      tap(battle => { this.currentBattle = battle; })
    );
  }

  playTurn(request: PlayTurnRequest): Observable<BattleTurnResponse> {
    return this.http.post<BattleTurnResponse>('http://localhost:8080/api/battle/turn', request);
  }

}
