import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RunService } from '../services/run.service';
import { BattleService } from '../services/battle.service';
import { Monster, Move, RunConfigurationResponse } from '../models/run.model';

@Component({
  selector: 'app-run-overview',
  templateUrl: './run-overview.component.html',
  styleUrl: './run-overview.component.scss',
})
export class RunOverviewComponent implements OnInit {

  runService = inject(RunService);
  private battleService = inject(BattleService);
  private router = inject(Router);
  run: RunConfigurationResponse | null = null;
  equippedMoves: Move[] = [];
  private readonly xpThresholds: Record<number, number> = { 1: 100, 2: 200, 3: 350, 4: 500 };

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

  isEquipped(move: Move): boolean {
    return this.equippedMoves.some(e => e.id === move.id);
  }

  monsterState(index: number): 'defeated' | 'current' | 'locked' {

    if (this.runService.runCompleted) return 'defeated';
    if (index < this.run!.currentMonsterIndex) return 'defeated';
    if (index === this.run!.currentMonsterIndex) return 'current';

    return 'locked';
    
  }

  xpThreshold(): string {
    const level = this.run?.hero.level ?? 0;

    if (level >= 5) return 'MAX';
    return this.xpThresholds[level]?.toString() ?? '';

  }

  xpPercent(): number {
    const level = this.run?.hero.level ?? 0;

    if (level >= 5) return 100;

    const threshold = this.xpThresholds[level];

    if (!threshold) return 0;
    
    return Math.min((this.run!.hero.xp / threshold) * 100, 100);
  }

}
