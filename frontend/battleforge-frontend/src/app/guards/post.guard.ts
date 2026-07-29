import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { BattleService } from "../services/battle.service";


export const postGuard: CanActivateFn = () => {

    const router = inject(Router);
    const battleService = inject(BattleService);

    if(battleService.lastResolve !== null) {
        return true;
    }
    
    return router.createUrlTree(['/main-menu']);

};