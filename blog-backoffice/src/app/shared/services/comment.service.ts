import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiUrl = 'http://localhost:8080/api/v1/comment';

  constructor(
    private http: HttpClient,
  ) {}

  getRecentComments(): Observable<any> {
    return this.http.get<any>(this.apiUrl).pipe(take(1), map((res: any) => res.content ));
  }
  getCommentsByPost(postId: number, page: number = 0, size: number = 10, sort: string = 'createdAt,desc'): Observable<any> {
    const url = `${this.apiUrl}/post/${postId}?page=${page}&size=${size}&sort=${sort}`;
    return this.http.get<any>(url).pipe(take(1));
  }
}