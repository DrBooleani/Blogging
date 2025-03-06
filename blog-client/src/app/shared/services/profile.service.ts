import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = `http://localhost:8080/api/v1/user`;
  private uploads = "http://localhost:8080/uploads/profile-photos/"; 

  constructor(private http: HttpClient) {}

  getUserProfile(id: number, authToken: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${authToken}`
    });

    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers }).pipe(
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
}
