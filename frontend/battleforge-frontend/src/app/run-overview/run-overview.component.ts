import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RunService } from '../services/run.service';
import { Monster, Move, RunConfigurationResponse } from '../models/run.model';

@Component({
  selector: 'app-run-overview',
  templateUrl: './run-overview.component.html',
})
export class RunOverviewComponent implements OnInit {

  private runService = inject(RunService);
  private router = inject(Router);
  run: RunConfigurationResponse | null = null;
  equippedMoves: Move[] = [];

  ngOnInit(): void {
    this.run = this.runService.currentRun;
    this.equippedMoves = this.runService.equippedMoves;
  }

  toggleEquip(move: Move): void {
    this.runService.toggleEquip(move);
    this.equippedMoves = this.runService.equippedMoves;
  }

  enterBattle(monster: Monster): void {

    if(this.equippedMoves.length < 4) {
      alert('You must equip all four moves before going to battle!')
    }else if (confirm(`Enter battle with ${monster.name} using your currently equipped moves?`)) {
      this.router.navigate(['/battle']);
    }
  }

}
