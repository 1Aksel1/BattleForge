import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Move, RunConfigurationResponse } from '../models/run.model';

@Injectable({ providedIn: 'root' })
export class RunService {

  private http = inject(HttpClient);
  currentRun: RunConfigurationResponse | null = null;
  currentRunId: number | null = null;
  equippedMoves: Move[] = [];
  runCompleted: boolean = false;

  public startRun(): Observable<RunConfigurationResponse> {

    return this.http.post<RunConfigurationResponse>('http://localhost:8080/api/run', {}).pipe(
      tap(run => {
        this.currentRun = run;
        this.currentRunId = run.runId;
        this.equippedMoves = run.hero.learnedMoves.slice(0, 4);
      })
    );

  }

  public getRun(runId: number): Observable<RunConfigurationResponse> {
    return this.http.get<RunConfigurationResponse>(`http://localhost:8080/api/run/${runId}`).pipe(
      tap(run => {
        this.currentRun = run;
        if (this.equippedMoves.length === 0) {
          this.equippedMoves = run.hero.learnedMoves.slice(0, 4);
        }
      })
    );
  }

  public abandonRun(runId: number): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/run/${runId}/abandon`, {});
  }

  public completeRun(runId: number): Observable<void> {
    return this.http.put<void>(`http://localhost:8080/api/run/${runId}/complete`, {});
  }

  public clearRun(): void {
    this.currentRun = null;
    this.currentRunId = null;
    this.equippedMoves = [];
    this.runCompleted = false;
  }

  public toggleEquip(move: Move): void {

    if (this.equippedMoves.some(e => e.id === move.id)) {
      this.equippedMoves = this.equippedMoves.filter(e => e.id !== move.id);
    } else if (this.equippedMoves.length < 4) {
      this.equippedMoves = [...this.equippedMoves, move];
    } else {
      alert('Unequip a move first before equipping a new one');
    }

  }

}
