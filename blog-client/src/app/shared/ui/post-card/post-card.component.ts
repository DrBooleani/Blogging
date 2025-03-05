import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PostResponse } from '../../interfaces/post/post-response';

@Component({
  selector: 'app-post-card',
  imports: [CommonModule, RouterLink],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.css'
})
export class PostCardComponent {
  @Input() post: PostResponse | null = null;

}
