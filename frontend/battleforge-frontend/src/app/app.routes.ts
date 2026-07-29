import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { guestGuard } from './guards/guest.guard';
import { runGuard } from './guards/run.guard';
import { battleGuard } from './guards/battle.guard';
import { postGuard } from './guards/post.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'main-menu',
    pathMatch: 'full',
  },
  {
    path: 'enter-username',
    loadComponent: () => import('./enter-username/enter-username.component').then(m => m.EnterUsernameComponent),
    canActivate: [guestGuard],
  },
  {
    path: 'main-menu',
    loadComponent: () => import('./main-menu/main-menu.component').then(m => m.MainMenuComponent),
    canActivate: [authGuard],
  },
  {
    path: 'run',
    loadComponent: () => import('./run-overview/run-overview.component').then(m => m.RunOverviewComponent),
    canActivate: [authGuard, runGuard],
  },
  {
    path: 'battle',
    loadComponent: () => import('./battle/battle.component').then(m => m.BattleComponent),
    canActivate: [authGuard, battleGuard],
  },
  {
    path: 'post-battle',
    loadComponent: () => import('./post-battle/post-battle.component').then(m => m.PostBattleComponent),
    canActivate: [authGuard, postGuard],
  },
  {
    path: '**',
    redirectTo: 'main-menu',
  },
];
