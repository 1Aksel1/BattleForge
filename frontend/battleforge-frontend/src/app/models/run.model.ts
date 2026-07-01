export interface MoveEffect {
  effectType: string;
  stat: string;
  target: string;
  value: number;
  duration: number | null;
}

export interface Move {
  id: number;
  name: string;
  description: string;
  primaryEffect: MoveEffect;
  secondaryEffect: MoveEffect | null;
}

export interface Hero {
  id: number;
  username: string;
  level: number;
  xp: number;
  health: number;
  attack: number;
  defense: number;
  magic: number;
  learnedMoves: Move[];
}

export interface Monster {
  id: number;
  name: string;
  imageUrl: string;
}

export interface RunConfigurationResponse {
  runId: number;
  currentMonsterIndex: number;
  hero: Hero;
  monsters: Monster[];
}
