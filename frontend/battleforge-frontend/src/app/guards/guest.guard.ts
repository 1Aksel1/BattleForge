import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const guestGuard: CanActivateFn = () => {
  const router = inject(Router);
  return localStorage.getItem('sessionId')
    ? router.createUrlTree(['/main-menu'])
    : true;
};
