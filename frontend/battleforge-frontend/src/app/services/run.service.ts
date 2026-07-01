import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Move, RunConfigurationResponse } from '../models/run.model';

@Injectable({ providedIn: 'root' })
export class RunService {

  private http = inject(HttpClient);
  currentRun: RunConfigurationResponse | null = null;
  equippedMoves: Move[] = [];

  public startRun(): Observable<RunConfigurationResponse> {

    return this.http.post<RunConfigurationResponse>('http://localhost:8080/api/run', {}).pipe(
      tap(run => {
        this.currentRun = run;
        this.equippedMoves = run.hero.learnedMoves.slice(0, 4);
      })
    );

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
