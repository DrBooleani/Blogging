import { Component, Input } from '@angular/core';
import { ProfileService } from '../../../../shared/services/profile.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css'],
  imports: [CommonModule, FormsModule]
})
export class UpdatePasswordComponent {
  @Input() userId: number = 0;
  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  error: string | null = null;

  constructor(private profileService: ProfileService) {}

  onSubmit() {
    if (this.newPassword === this.confirmPassword) {
      this.profileService.updateUserPassword(this.userId, this.oldPassword, this.newPassword, this.confirmPassword).subscribe({
        next: (response: any) => {
          console.log('Password updated successfully');
          if (response) {
            window.location.href = "/auth/profile";
          }
            
        },
        error: (err: any) => {
          this.error = 'Failed to update password';
        }
      });
    } else {
      this.error = 'Passwords do not match';
    }
  }
}
