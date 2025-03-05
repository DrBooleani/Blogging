import { Component, OnInit, OnDestroy } from '@angular/core';
import { BehaviorSubject, Subscription } from 'rxjs';
import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import { PostCardComponent } from '../../../shared/ui/post-card/post-card.component';
import { PostPageResponse } from '../../../shared/interfaces/post/post-page-response';
import { PostService } from '../../../shared/services/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
  imports: [PostCardComponent, NgFor, NgIf, AsyncPipe]
})
export class PostListComponent implements OnInit, OnDestroy {

  posts$ = new BehaviorSubject<PostPageResponse['content']>([]);
  totalPages: number = 0;
  currentPage: number = 0;
  private subscription: Subscription | null = null;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  loadPosts(page: number = 0): void {
    this.subscription = this.postService.getAllPosts(page).subscribe({
      next: (response: PostPageResponse) => {
        this.posts$.next(response.content);
        this.totalPages = response.page.totalPages;
        this.currentPage = page;
      },
      error: (error) => {
        console.error('Error on loading posts ', error);
      }
    });
  }

  goToPreviousPage(): void {
    if (this.currentPage > 0) {
      this.loadPosts(this.currentPage - 1);
    }
  }

  goToNextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.loadPosts(this.currentPage + 1);
    }
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.loadPosts(page);
    }
  }
}
