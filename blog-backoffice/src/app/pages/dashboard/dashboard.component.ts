import { Component, OnDestroy, OnInit } from '@angular/core';
import { PostPageResponse } from '../../shared/interfaces/post-page-response';
import { UserResponse } from '../../shared/interfaces/user-response';
import { CommentResponse } from '../../shared/interfaces/comment-response';
import { PostService } from '../../shared/services/post.service';
import { UserService } from '../../shared/services/user.service';
import { CommentService } from '../../shared/services/comment.service';
import { Subject } from 'rxjs';
import { RecentPostsComponent } from "./recent-posts/recent-posts.component";
import { RecentUsersComponent } from "./recent-users/recent-users.component";
import { RecentCommentsComponent } from "./recent-comments/recent-comments.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  imports: [RecentPostsComponent, RecentUsersComponent, RecentCommentsComponent]
})
export class DashboardComponent implements OnInit, OnDestroy {
  recentPosts: PostPageResponse = { content: [], page: { size: 0, number: 0, totalElements: 0, totalPages: 0 } };
  recentUsers: UserResponse[] = [];
  recentComments: CommentResponse[] = [];

  destroy$ = new Subject<void>();

  constructor(
    private postService: PostService,
    private userService: UserService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.loadRecentPosts();
    this.loadRecentUsers();
    this.loadRecentComments();
  }

  loadRecentPosts(): void {
    this.postService.getAllPosts(5, 0).subscribe((response) => {
      this.recentPosts = response;
    });
  }

  loadRecentUsers(): void {
    this.userService.getAllUsers(0, 5).subscribe((users) => {
      this.recentUsers = users.content;
    });
  }

  loadRecentComments(): void {
    this.commentService.getRecentComments().subscribe((comments) => {
      this.recentComments = comments;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
