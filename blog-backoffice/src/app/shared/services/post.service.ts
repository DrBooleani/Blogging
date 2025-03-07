import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, take } from 'rxjs';
import { AuthService } from './auth.service';
import { PostResponse } from '../interfaces/post-response';
import { PostPageResponse } from '../interfaces/post-page-response';
import { PostRequest } from '../interfaces/post-request';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl = 'http://localhost:8080/api/v1/post';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllPosts(size: number = 10, page: number = 0, sort: string = 'createdAt,desc'): Observable<PostPageResponse> {
    return this.http.get<PostPageResponse>(`${this.apiUrl}?size=${size}&page=${page}&sort=${sort}`).pipe(take(1));
  }

  getPostById(id: number): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.apiUrl}/${id}`).pipe(take(1));
  }

  createPost(postRequest: PostRequest, thumbnailFile?: File): Observable<PostResponse> {
    const formData = new FormData();
    formData.append('title', postRequest.title);
    formData.append('content', postRequest.content);
    formData.append('category', postRequest.category);
    formData.append('tags', JSON.stringify(postRequest.tags));

    if (thumbnailFile) {
      formData.append('thumbnailFile', thumbnailFile, thumbnailFile.name);
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.post<PostResponse>(this.apiUrl, formData, { headers }).pipe(take(1));
  }

  updatePost(id: number, postRequest: PostRequest, thumbnailFile?: File): Observable<PostResponse> {
    const formData = new FormData();
    formData.append('title', postRequest.title);
    formData.append('content', postRequest.content);
    formData.append('category', postRequest.category);
    formData.append('tags', JSON.stringify(postRequest.tags));

    if (thumbnailFile) {
      formData.append('thumbnailFile', thumbnailFile, thumbnailFile.name);
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.put<PostResponse>(`${this.apiUrl}/${id}`, formData, { headers }).pipe(take(1));
  }

  updatePostThumbnail(id: number, thumbnailFile: File): Observable<PostResponse> {
    const formData = new FormData();
    formData.append('thumbnailFile', thumbnailFile, thumbnailFile.name);

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.patch<PostResponse>(`${this.apiUrl}/${id}/thumbnail`, formData, { headers }).pipe(take(1));
  }

  deletePost(id: number): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers }).pipe(take(1));
  }
}
