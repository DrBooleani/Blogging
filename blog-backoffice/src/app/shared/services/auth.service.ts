import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, take } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';

interface LoginResponse {
  token: string;
  expiresIn: number;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private authUrl = 'http://localhost:8080/api/v1/auth/login';
  
  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) {}

    getUserIdFromToken(): number {
      const token = this.cookieService.get('authToken');
      if (token) {
        const decodedToken: any = jwtDecode(token);
        return decodedToken.id;
      }
      return -1;
    }
  

  login(email: string, password: string): Observable<LoginResponse> {
    const loginRequest = { email, password };
    return this.http.post<LoginResponse>(this.authUrl, loginRequest).pipe(take(1));
  }

  storeToken(token: string, expirationTime: number): void {
    this.cookieService.set('authToken', token, expirationTime, '/');
  }

  isLoggedIn(): boolean {
    return this.cookieService.check('authToken');
  }

  getToken(): string {
    return this.cookieService.get('authToken');
  }

  logout(): void {
    this.cookieService.delete('authToken', '/');
  }
}
