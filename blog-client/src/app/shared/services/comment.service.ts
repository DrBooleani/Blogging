import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { UserCommentRequest } from '../interfaces/comment/user-comment-request';
import { CommentResponse } from '../interfaces/comment/comment-response';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiUrl = 'http://localhost:8080/api/v1/comment';

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) {}

  private getUserIdFromToken(): number {
    const token = this.cookieService.get('authToken');
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken.id;
    }
    return -1;
  }

  private getToken(): string {
    return this.cookieService.get('authToken');
  }

  createComment(postId: number, content: string): Observable<CommentResponse> {
    const userId = this.getUserIdFromToken();
    console.log(userId);
    if (userId === -1) {
      throw new Error('User not authenticated!');
    }

    const userCommentRequest: UserCommentRequest = {
      postId: postId,
      content: content,
      userId: userId
    };

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });

    return this.http.post<CommentResponse>(this.apiUrl, userCommentRequest, { headers });
  }

  getCommentsByPost(postId: number, page: number = 0, size: number = 10, sort: string = 'createdAt,desc'): Observable<any> {
    const url = `${this.apiUrl}/post/${postId}?page=${page}&size=${size}&sort=${sort}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });

    return this.http.get<any>(url, { headers });
  }

  updateComment(commentId: number, content: string): Observable<CommentResponse> {
    const userId = this.getUserIdFromToken();
    if (userId === -1) {
      throw new Error('Usuário não autenticado');
    }

    const userCommentRequest: UserCommentRequest = {
      postId: 0,
      content: content,
      userId: userId
    };

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });

    return this.http.put<CommentResponse>(`${this.apiUrl}/${commentId}`, userCommentRequest, { headers });
  }

  deleteComment(commentId: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${commentId}`, { headers });
  }
}
