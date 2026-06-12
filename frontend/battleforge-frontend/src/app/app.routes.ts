import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./main-menu/main-menu.component').then(m => m.MainMenuComponent),
  },
  {
    path: 'run',
    loadComponent: () => import('./run-overview/run-overview.component').then(m => m.RunOverviewComponent),
  },
  {
    path: 'battle',
    loadComponent: () => import('./battle/battle.component').then(m => m.BattleComponent),
  },
  {
    path: 'post-battle',
    loadComponent: () => import('./post-battle/post-battle.component').then(m => m.PostBattleComponent),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
