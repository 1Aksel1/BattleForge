import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BattleService } from '../services/battle.service';
import { BattleStateDto } from '../models/battle.model';

@Component({
  selector: 'app-battle',
  templateUrl: './battle.component.html',
})
export class BattleComponent implements OnInit {

  private battleService = inject(BattleService);
  private router = inject(Router);

  battle: BattleStateDto | null = null;
  isWaiting: boolean = false;
  lastMonsterMove: string | null = null;

  ngOnInit(): void {
    this.battle = this.battleService.currentBattle;
  }

  playTurn(moveId: number): void {
    this.isWaiting = true;
    this.battleService.playTurn({ battleStateId: this.battle!.battleStateId, moveId }).subscribe({
      next: (response) => {
        this.battle!.monster.currentHp = response.monster.currentHp;
        setTimeout(() => {
          this.battle!.hero.currentHp = response.hero.currentHp;
          this.lastMonsterMove = response.monsterMoveName;
          if (response.battleOver) {
            alert('Winner: ' + response.winner);
            this.router.navigate(['/run']);
          }
          this.isWaiting = false;
        }, 1000);
      },
      error: (err) => {
        alert(err.error.message);
        this.isWaiting = false;
      }
    });
  }

}
