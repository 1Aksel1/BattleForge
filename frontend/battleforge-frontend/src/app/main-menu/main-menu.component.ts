import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { RunService } from '../services/run.service';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
})
export class MainMenuComponent {

  private runService = inject(RunService);
  private router = inject(Router);

  startRun(): void {
    this.runService.startRun().subscribe({
      next: () => this.router.navigate(['/run']),
      error: (err) => alert(err.message),
    });
  }
  
}
