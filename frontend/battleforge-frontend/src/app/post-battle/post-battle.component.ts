import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BattleService } from '../services/battle.service';
import { BattleResolveResponse } from '../models/battle.model';

@Component({
  selector: 'app-post-battle',
  templateUrl: './post-battle.component.html',
})
export class PostBattleComponent implements OnInit {

  private battleService = inject(BattleService);
  private router = inject(Router);

  resolve: BattleResolveResponse | null = null;

  ngOnInit(): void {
    this.resolve = this.battleService.lastResolve;
  }

  continue(): void {
    this.router.navigate(['/run']);
  }

}
