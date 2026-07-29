import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RunService } from '../services/run.service';
import { BattleService } from '../services/battle.service';
import { Monster, Move, RunConfigurationResponse } from '../models/run.model';

@Component({
  selector: 'app-run-overview',
  templateUrl: './run-overview.component.html',
})
export class RunOverviewComponent implements OnInit {

  runService = inject(RunService);
  private battleService = inject(BattleService);
  private router = inject(Router);
  run: RunConfigurationResponse | null = null;
  equippedMoves: Move[] = [];

  ngOnInit(): void {

    this.runService.getRun(this.runService.currentRunId!).subscribe({
      next: (run) => {
        this.run = run;
        this.equippedMoves = this.runService.equippedMoves;
      },
      error: (err) => alert(err.error.message),
    });
    
  }

  toggleEquip(move: Move): void {
    this.runService.toggleEquip(move);
    this.equippedMoves = this.runService.equippedMoves;
  }

  mainMenu(): void {
    this.router.navigate(['/main-menu']);
  }

  abandonRun(): void {

    if (!confirm('Abandon this run? This cannot be undone.')) return;

    this.runService.abandonRun(this.runService.currentRunId!).subscribe({
      next: () => {
        this.runService.clearRun();
        this.router.navigate(['/main-menu']);
      },
      error: (err) => alert(err.error?.message ?? err.message),
    });

  }

  completeRun(): void {

    if (!confirm('Complete this run? You will return to the main menu.')) return;

    this.runService.completeRun(this.runService.currentRunId!).subscribe({
      next: () => {
        this.runService.clearRun();
        this.router.navigate(['/main-menu']);
      },
      error: (err) => alert(err.error?.message ?? err.message),
    });

  }

  enterBattle(monster: Monster): void {

    if (this.equippedMoves.length < 4) {
      alert('You must equip all four moves before going to battle!');
      return;
    }

    if (!confirm(`Enter battle with ${monster.name} using your currently equipped moves?`)) {
      return;
    }

    this.battleService.startBattle({
      runId: this.run!.runId,
      monsterId: monster.id,
      equippedMoveIds: this.equippedMoves.map(m => m.id),
    }).subscribe({
      next: () => this.router.navigate(['/battle']),
      error: (err) => alert(err.message),
    });

  }

  xpThreshold(): string {

    switch(this.run?.hero.level) {

      case 1: return "100";
      case 2: return "200";
      case 3: return "350";
      case 4: return "500";
      case 5: return "MAX";
      default:  return "";
      
    }

  }

}
