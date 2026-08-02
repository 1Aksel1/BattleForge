import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BattleService } from '../services/battle.service';
import { BattleStateDto, HeroBattleStateDto, MonsterBattleStateDto } from '../models/battle.model';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-battle',
  templateUrl: './battle.component.html',
  styleUrl: './battle.component.scss',
  imports: [DecimalPipe]
})
export class BattleComponent implements OnInit {

  private battleService = inject(BattleService);
  private router = inject(Router);

  battle: BattleStateDto | null = null;
  isWaiting: boolean = false;
  heroMessage: string | null = null;
  monsterMessage: string | null = null;

  heroCurrentHp: number = 0;
  heroMaxHp: number = 0;
  heroAttack: number = 0;
  heroDefense: number = 0;
  heroMagic: number = 0;

  monsterCurrentHp: number = 0;
  monsterMaxHp: number = 0;
  monsterAttack: number = 0;
  monsterDefense: number = 0;
  monsterMagic: number = 0;

  ngOnInit(): void {

    this.battle = this.battleService.currentBattle;

    if (this.battle) {
      this.applySnapshot(this.battle.hero, this.battle.monster);
    }

  }

  mainMenu(): void {
    this.router.navigate(['/main-menu']);
  }

  abandonBattle(): void {

    if (!confirm('Abandon this battle? This cannot be undone.')) return;

    const id = this.battle?.battleStateId ?? this.battleService.currentBattleStateId!;

    this.battleService.abandonBattle(id).subscribe({
      next: () => {
        this.battleService.clearBattle();
        this.router.navigate(['/main-menu']);
      },
      error: (err) => alert(err.error?.message ?? err.message),
    });

  }

  private applySnapshot(hero: HeroBattleStateDto, monster: MonsterBattleStateDto): void {

    this.heroCurrentHp = hero.currentHp;
    this.heroMaxHp = hero.maxHp;
    this.heroAttack = hero.attack;
    this.heroDefense = hero.defense;
    this.heroMagic = hero.magic;

    this.monsterCurrentHp = monster.currentHp;
    this.monsterMaxHp = monster.maxHp;
    this.monsterAttack = monster.attack;
    this.monsterDefense = monster.defense;
    this.monsterMagic = monster.magic;

  }

  monsterImagePath(): string {
    const name = this.battle?.monster.name ?? '';
    return `characters/${name.toLowerCase().replace(/\s+/g, '-')}.png`;
  }

  hpPercent(current: number, max: number): number {
    if (max === 0) return 0;
    return Math.max(0, Math.min((current / max) * 100, 100));
  }

  isLowHp(current: number, max: number): boolean {
    if (max === 0) return false;
    return current / max < 0.25;
  }

  playTurn(moveId: number): void {

    this.isWaiting = true;

    this.battleService.playTurn({ battleStateId: this.battle!.battleStateId, moveId }).subscribe({

      next: (response) => {

        const monsterHpBefore = this.monsterCurrentHp;
        const heroMoveName = this.battle!.hero.equippedMoves.find(m => m.id === moveId)?.name ?? 'Unknown';

        this.applySnapshot(response.heroAfterHeroMove, response.monsterAfterHeroMove);

        if (this.battleService.currentBattle) {
          this.battleService.currentBattle = {
            ...this.battleService.currentBattle,
            hero: response.heroAfterHeroMove,
            monster: response.monsterAfterHeroMove,
          };
        }

        const heroDamage = Math.max(0, Math.round(monsterHpBefore - response.monsterAfterHeroMove.currentHp));
        this.heroMessage = `You used ${heroMoveName}!${heroDamage > 0 ? ` (−${heroDamage} HP)` : ''}`;
        this.monsterMessage = null;

        if (response.battleOver && response.winner === 'HERO') {

          setTimeout(() => {

            this.battleService.resolveBattle(response.battleStateId).subscribe({
              next: () => {
                this.battleService.clearBattle();
                this.router.navigate(['/post-battle']);
              },
              error: (err) => {
                alert(err.error.message);
                this.isWaiting = false;
              }
            });

          }, 1250);

          return;

        }

        setTimeout(() => {

          const monsterDamage = Math.max(0, Math.round(response.heroAfterHeroMove.currentHp - response.heroAfterMonsterMove!.currentHp));
          this.monsterMessage = `${this.battle!.monster.name} used ${response.monsterMoveName}!${monsterDamage > 0 ? ` (−${monsterDamage} HP)` : ''}`;

          this.applySnapshot(response.heroAfterMonsterMove!, response.monsterAfterMonsterMove!);

          if (this.battleService.currentBattle) {
            this.battleService.currentBattle = {
              ...this.battleService.currentBattle,
              hero: response.heroAfterMonsterMove!,
              monster: response.monsterAfterMonsterMove!,
            };
          }

          if (response.battleOver && response.winner === 'MONSTER') {
            alert('You lost!');
            this.battleService.clearBattle();
            this.router.navigate(['/run']);
            return;
          }

          this.isWaiting = false;

        }, 1500);

      },

      error: (err) => {
        alert(err.error.message);
        this.isWaiting = false;
      }

    });

  }

}
