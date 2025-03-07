import { Component, Input } from '@angular/core';
import { CommentResponse } from '../../../shared/interfaces/comment-response';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recent-comments',
  imports: [CommonModule],
  templateUrl: './recent-comments.component.html',
  styleUrl: './recent-comments.component.css',
})
export class RecentCommentsComponent {
  @Input() comments: CommentResponse[] = [];

}
