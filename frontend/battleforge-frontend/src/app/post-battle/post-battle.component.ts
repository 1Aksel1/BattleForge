import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BattleService } from '../services/battle.service';
import { RunService } from '../services/run.service';
import { BattleResolveResponse } from '../models/battle.model';

@Component({
  selector: 'app-post-battle',
  templateUrl: './post-battle.component.html',
  styleUrl: './post-battle.component.scss',
})
export class PostBattleComponent implements OnInit {

  private battleService = inject(BattleService);
  private runService = inject(RunService);
  private router = inject(Router);

  resolve: BattleResolveResponse | null = null;

  ngOnInit(): void {
    this.resolve = this.battleService.lastResolve;
  }

  continue(): void {

    if (this.battleService.lastResolve?.runComplete) {
      this.runService.runCompleted = true;
    }
    
    this.router.navigate(['/run']);
  }

}
