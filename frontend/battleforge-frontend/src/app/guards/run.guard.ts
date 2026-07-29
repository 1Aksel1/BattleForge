import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { BattleService } from '../services/battle.service';
import { RunService } from '../services/run.service';

export const runGuard: CanActivateFn = () => {

  const battleService = inject(BattleService);
  const runService = inject(RunService);
  const router = inject(Router);

  if (battleService.currentBattle !== null || battleService.currentBattleStateId !== null) {
    return router.createUrlTree(['/battle']);
  }

  if(runService.currentRun === null || runService.currentRunId === null) {
    return router.createUrlTree(['/main-menu']);
  }

  return true;

};
