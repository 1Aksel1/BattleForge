import { Move } from './run.model';

export interface StartBattleRequest {
  runId: number;
  monsterId: number;
  equippedMoveIds: number[];
}

export interface HeroBattleStateDto {
  currentHp: number;
  maxHp: number;
  attack: number;
  defense: number;
  magic: number;
  equippedMoves: Move[];
}

export interface MonsterBattleStateDto {
  name: string;
  currentHp: number;
  maxHp: number;
  attack: number;
  defense: number;
  magic: number;
}

export interface BattleStateDto {
  battleStateId: number;
  hero: HeroBattleStateDto;
  monster: MonsterBattleStateDto;
}

export interface PlayTurnRequest {
  battleStateId: number;
  moveId: number;
}

export interface BattleTurnResponse {
  battleStateId: number;
  hero: HeroBattleStateDto;
  monster: MonsterBattleStateDto;
  monsterMoveName: string | null;
  battleOver: boolean;
  winner: 'HERO' | 'MONSTER' | null;
}
