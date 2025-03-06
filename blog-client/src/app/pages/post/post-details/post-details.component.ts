import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PostService } from '../../../shared/services/post.service';
import { PostResponse } from '../../../shared/interfaces/post/post-response';
import { CommonModule } from '@angular/common';
import { CommentsSectionComponent } from "./comments-section/comments-section.component";

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css'],
  imports: [CommonModule, RouterLink, CommentsSectionComponent]
})
export class PostDetailsComponent implements OnInit {

  post: PostResponse | null = null;
  isLoading: boolean = true;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const postId = +this.route.snapshot.paramMap.get('id')!;
    this.loadPost(postId);
  }

  loadPost(id: number): void {
    this.postService.getPostById(id).subscribe({
      next: (post) => {
        this.post = post;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading post:', err);
        this.isLoading = false;
      }
    });
  }
}
