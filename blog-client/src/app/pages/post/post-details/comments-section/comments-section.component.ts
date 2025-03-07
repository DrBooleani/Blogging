import { Component, Input, OnInit } from '@angular/core'; 
import { CommentResponse } from '../../../../shared/interfaces/comment/comment-response';
import { CommentService } from '../../../../shared/services/comment.service';
import { AuthService } from '../../../../shared/services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.css'],
  imports: [CommonModule, FormsModule]
})
export class CommentsSectionComponent implements OnInit {
  userId: number = 0;
  @Input() postId!: number;
  comments: CommentResponse[] = [];
  newCommentContent: string = '';
  isLoggedIn: boolean = false;
  uploads: string = 'http://localhost:8080/uploads/profile-photos/';
  
  constructor(
    private commentService: CommentService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.userId = this.authService.getUserIdFromToken();
    this.loadComments();
  }

  loadComments(): void {
    this.commentService.getCommentsByPost(this.postId).subscribe({
      next: (response) => {
        this.comments = response.content;
      },
      error: (err) => {
        console.error('Error loading comments:', err);
      }
    });
  }

  submitComment(): void {
    if (this.newCommentContent.trim()) {
      this.commentService.createComment(this.postId, this.newCommentContent).subscribe({
        next: (newComment) => {
          this.comments.push(newComment);
          this.newCommentContent = '';
        },
        error: (err) => {
          console.error('Error submitting comment:', err);
        }
      });
    }
  }

  editComment(comment: CommentResponse): void {
    comment.editing = true;
  }

  updateComment(comment: CommentResponse): void {
    if (comment.content.trim()) {
      this.commentService.updateComment(comment.id, comment.content).subscribe({
        next: (updatedComment) => {
          comment.content = updatedComment.content;
          comment.editing = false;
        },
        error: (err) => {
          console.error('Error updating comment:', err);
        }
      });
    }
  }

  deleteComment(commentId: number): void {
    this.commentService.deleteComment(commentId).subscribe({
      next: () => {
        this.comments = this.comments.filter(comment => comment.id !== commentId);
      },
      error: (err) => {
        console.error('Error deleting comment:', err);
      }
    });
  }
}
