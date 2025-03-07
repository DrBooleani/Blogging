import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable, take } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `http://localhost:8080/api/v1/user`;
  private uploads = "http://localhost:8080/uploads/profile-photos/";

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllUsers(page: number = 0, size: number = 10, sort: string = 'fullName,asc'): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}&sort=${sort}`).pipe(
      take(1)
    );
  }

  getUserProfile(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(
      take(1),
      map((response) => {
        return {
          ...response,
          profileUrl: this.uploads + response.profileUrl
        };
      })
    );
  }

  createUser(fullName: string, email: string, password: string): Observable<any> {
    const userRequest = { fullName, email, password };
    return this.http.post<any>(`${this.apiUrl}`, userRequest);
  }

  updateProfilePhoto(id: number, file: File): Observable<any> {
    const authToken = this.authService.getToken();
    const formData = new FormData();
    formData.append('file', file, file.name);
    
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
    });

    return this.http.patch<any>(`${this.apiUrl}/${id}/profile-photo`, formData, { headers }).pipe(take(1));
  }

  updateUserPassword(id: number, oldPassword: string, newPassword: string, confirmPassword: string): Observable<any> {
    const authToken = this.authService.getToken();
    const body = {
      oldPassword,
      newPassword,
      confirmPassword,
    };

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
    });

    return this.http.patch<any>(`${this.apiUrl}/${id}/update-password`, body, { headers }).pipe(take(1));
  }

  makeUserAdmin(id: number): Observable<any> {
    const authToken = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
    });

    return this.http.patch<any>(`${this.apiUrl}/${id}/make-admin`, {}, { headers }).pipe(take(1));
  }

  deleteUser(id: number): Observable<void> {
    const authToken = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`,
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers }).pipe(take(1));
  }
}