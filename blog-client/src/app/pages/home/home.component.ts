import { Component, OnInit, OnDestroy } from '@angular/core';
import { PostService } from '../../shared/services/post.service';
import { BehaviorSubject, Subscription } from 'rxjs';
import { PostPageResponse } from '../../shared/interfaces/post/post-page-response';
import { PostCardComponent } from "../../shared/ui/post-card/post-card.component";
import { AsyncPipe, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [PostCardComponent, NgFor, NgIf, AsyncPipe]
})
export class HomeComponent implements OnInit, OnDestroy {

  posts$ = new BehaviorSubject<PostPageResponse['content']>([]); 
  private subscription: Subscription | null = null;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.subscription = this.postService.getAllPosts().subscribe({
      next: posts => this.posts$.next(posts.content),
      error: error => console.error('Error fetching posts', error)
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
