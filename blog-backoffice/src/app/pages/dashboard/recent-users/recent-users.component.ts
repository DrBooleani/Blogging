import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { UserResponse } from '../../../shared/interfaces/user-response';
@Component({
  selector: 'app-recent-users',
  templateUrl: './recent-users.component.html',
  styleUrls: ['./recent-users.component.css'],
  imports: [CommonModule]
})
export class RecentUsersComponent {
  @Input() users: UserResponse[] = [];
}
