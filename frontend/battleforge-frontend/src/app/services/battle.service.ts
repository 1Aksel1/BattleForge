import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { BattleResolveResponse, BattleStateDto, BattleTurnResponse, PlayTurnRequest, StartBattleRequest } from '../models/battle.model';

@Injectable({ providedIn: 'root' })
export class BattleService {

  private http = inject(HttpClient);
  
  currentBattle: BattleStateDto | null = null;
  currentBattleStateId: number | null = null;
  lastResolve: BattleResolveResponse | null = null;

  startBattle(request: StartBattleRequest): Observable<BattleStateDto> {
    return this.http.post<BattleStateDto>('/api/battle/start', request).pipe(
      tap(battle => {
        this.currentBattle = battle;
        this.currentBattleStateId = battle.battleStateId;
      })
    );
  }

  playTurn(request: PlayTurnRequest): Observable<BattleTurnResponse> {
    return this.http.post<BattleTurnResponse>('/api/battle/turn', request);
  }

  resolveBattle(battleStateId: number): Observable<BattleResolveResponse> {
    return this.http.post<BattleResolveResponse>(
      `/api/battle/resolve/${battleStateId}`, {}
    ).pipe(tap(resolve => { this.lastResolve = resolve; }));
  }

  abandonBattle(battleStateId: number): Observable<void> {
    return this.http.put<void>(`/api/battle/${battleStateId}/abandon`, {});
  }

  clearBattle(): void {
    this.currentBattle = null;
    this.currentBattleStateId = null;
  }

}
