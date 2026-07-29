import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionResponse, SessionStatusResponse } from '../models/session.model';

@Injectable({ providedIn: 'root' })
export class SessionService {

  private http = inject(HttpClient);

  create(username: string): Observable<SessionResponse> {
    return this.http.post<SessionResponse>('http://localhost:8080/api/session', { username });
  }

  getStatus(): Observable<SessionStatusResponse> {
    return this.http.get<SessionStatusResponse>('http://localhost:8080/api/session/status');
  }

}
