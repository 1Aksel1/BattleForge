import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-enter-username',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div>
      <h2>Enter Username</h2>
      <input [(ngModel)]="username" placeholder="Username" />
      <button (click)="onSubmit()">Enter</button>
    </div>
  `,
})
export class EnterUsernameComponent {

  private sessionService = inject(SessionService);
  private router = inject(Router);

  username = '';

  onSubmit(): void {
    this.sessionService.create(this.username).subscribe({
      next: ({ sessionId }) => {
        localStorage.setItem('sessionId', sessionId);
        this.router.navigate(['/main-menu']);
      },
      error: (err) => {
        alert(err?.error?.message ?? err?.message ?? 'Failed to create session');
        this.username = '';
      },
    });
  }

}
