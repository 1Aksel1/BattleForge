import { Component, inject, OnInit } from '@angular/core';
import { BattleService } from '../services/battle.service';
import { BattleStateDto } from '../models/battle.model';

@Component({
  selector: 'app-battle',
  templateUrl: './battle.component.html',
})
export class BattleComponent implements OnInit {

  private battleService = inject(BattleService);
  battle: BattleStateDto | null = null;

  ngOnInit(): void {
    this.battle = this.battleService.currentBattle;
  }

}
