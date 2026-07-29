import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { BattleService } from '../services/battle.service';

export const battleGuard: CanActivateFn = () => {

  const battleService = inject(BattleService);
  const router = inject(Router);

  if (battleService.currentBattle === null && battleService.currentBattleStateId === null) {
    return router.createUrlTree(['/run']);
  }
  
  return true;
};
