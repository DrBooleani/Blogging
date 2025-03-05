import { Component, Input } from '@angular/core';
import { PostPageResponse } from '../../interfaces/post/post-page-response';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-post-card',
  imports: [CommonModule, RouterLink],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.css'
})
export class PostCardComponent {
  @Input() post: PostPageResponse | null = null;

}
