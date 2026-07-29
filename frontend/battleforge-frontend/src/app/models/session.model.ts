export interface SessionStatusResponse {
  hasActiveRun: boolean;
  runId: number | null;
  hasActiveBattle: boolean;
  battleStateId: number | null;
}

export interface SessionResponse {
  sessionId: string;
}
