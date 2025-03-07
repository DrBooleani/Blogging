import { Component, Input } from '@angular/core';
import { PostPageResponse } from '../../../shared/interfaces/post-page-response';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recent-posts',
  templateUrl: './recent-posts.component.html',
  styleUrls: ['./recent-posts.component.css'],
  imports: [CommonModule]
})
export class RecentPostsComponent {
  @Input() posts: PostPageResponse = { content: [], page: { size: 0, number: 0, totalElements: 0, totalPages: 0 } };
}
