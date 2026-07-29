import { HttpInterceptorFn } from '@angular/common/http';

export const sessionInterceptor: HttpInterceptorFn = (req, next) => {

  const sessionId = localStorage.getItem('sessionId');
  
  if (!sessionId) return next(req);
  return next(req.clone({ setHeaders: { 'X-Session-Id': sessionId } }));

};
