import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { RunService } from '../services/run.service';
import { BattleService } from '../services/battle.service';
import { SessionStatusResponse } from '../models/session.model';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.scss',
  imports: [],
})
export class MainMenuComponent implements OnInit {

  private sessionService = inject(SessionService);
  private runService = inject(RunService);
  private battleService = inject(BattleService);
  private router = inject(Router);

  username = localStorage.getItem('username') ?? 'Hero';
  status: SessionStatusResponse | null = null;

  ngOnInit(): void {
    this.sessionService.getStatus().subscribe({
      next: (status) => { this.status = status; },
      error: (err) => alert(err.error?.message ?? err.message),
    });
  }

  startRun(): void {

    if (this.status?.hasActiveRun) {

      if (!confirm('A run is already in progress. It will be abandoned. Continue?')) return;

      this.runService.abandonRun(this.status.runId!).subscribe({
        next: () => {
          this.battleService.currentBattle = null;
          this.battleService.currentBattleStateId = null;
          this.doStartRun();
        },
        error: (err) => alert(err.error?.message ?? err.message),
      });

    } else {
      this.doStartRun();
    }

  }

  private doStartRun(): void {

    this.runService.startRun().subscribe({
      next: () => this.router.navigate(['/run']),
      error: (err) => alert(err.error?.message ?? err.message),
    });
    
  }

  continueRun(): void {
    this.runService.currentRunId = this.status!.runId!;
    this.router.navigate(['/run']);
  }

  continueBattle(): void {
    this.battleService.currentBattleStateId = this.status!.battleStateId!;
    this.router.navigate(['/battle']);
  }

  exit(): void {
    localStorage.clear();
    this.runService.clearRun();
    this.battleService.clearBattle();
    this.router.navigate(['/enter-username']);
  }

}
