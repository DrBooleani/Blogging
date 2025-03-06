import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthStateService {
  public readonly isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private authService: AuthService) {
    this.isLoggedInSubject = new BehaviorSubject<boolean>(this.authService.isLoggedIn());
  }

  updateLoginState(): void {
    this.isLoggedInSubject.next(this.authService.isLoggedIn());
    console.log(this.isLoggedInSubject);
  }

  getLoginState(): boolean {
    return this.isLoggedInSubject.value;
  }
  
  login(email: string, password: string) {
    this.authService.login(email, password).subscribe(response => {
      this.authService.storeToken(response.token, response.expiresIn);
      this.updateLoginState();
    });
  }

  logout(): void {
    this.authService.logout();
    this.updateLoginState();
  }
}
