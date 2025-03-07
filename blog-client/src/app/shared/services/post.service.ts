import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostPageResponse } from '../interfaces/post/post-page-response';
import { map, take } from 'rxjs/operators';
import { PostResponse } from '../interfaces/post/post-response';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl = "http://localhost:8080/api/v1/post";
  private uploads = "http://localhost:8080/uploads/post-thumbnails/";

  constructor(private http: HttpClient) {}

  getAllPosts(page: number = 0, size: number = 10, sort: string = 'createdAt,desc'): Observable<PostPageResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    return this.http.get<PostPageResponse>(this.baseUrl, { params }).pipe(
      take(1),
      map((response) => {
        response.content = response.content.map((post: any) => ({
          ...post,
          thumbnail: this.uploads + post.thumbnail
        }));
        return response;
      })
    );
  }

  getPostById(id: number): Observable<PostResponse> {
    return this.http.get<PostResponse>(`${this.baseUrl}/${id}`).pipe(
      take(1),
      map((response) => ({
        ...response,
        thumbnail: this.uploads + response.thumbnail
      }))
    );
  }
}
