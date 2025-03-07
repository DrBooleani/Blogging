import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProfileService } from '../../../../shared/services/profile.service';

@Component({
  selector: 'app-update-profile-photo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mt-4">
      <h2 class="text-center">Update Profile Picture</h2>

      <div *ngIf="successMessage" class="alert alert-success">
        {{ successMessage }}
      </div>

      <div *ngIf="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div class="card p-4 mt-3">
        <form>
          <div class="mb-3">
            <label for="fileInput" class="form-label">Choose a Picture</label>
            <input type="file" class="form-control" id="fileInput" (change)="onFileSelected($event)" />
          </div>
          <div class="d-flex justify-content-center">
            <button 
              type="button" 
              class="btn btn-primary" 
              [disabled]="!selectedFile || isUploading" 
              (click)="onUpload()">
              <span *ngIf="isUploading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
              {{ isUploading ? 'Uploading...' : 'Upload Photo' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .container {
      max-width: 500px;
    }

    .card {
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    button:disabled {
      background-color: #d6d6d6;
    }
  `]
})
export class UpdateProfilePhotoComponent {

  @Input() userId: number = 0;
  selectedFile: File | null = null;
  isUploading: boolean = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private profileService: ProfileService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files?.length) {
      this.selectedFile = input.files[0];
      this.successMessage = null;
      this.errorMessage = null;
    }
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.isUploading = true;
      this.profileService.updateProfilePhoto(this.userId, this.selectedFile).subscribe(
        (response) => {
          this.isUploading = false;
          this.successMessage = 'Profile photo updated successfully!';
          this.errorMessage = null;
        },
        (error) => {
          this.isUploading = false;
          this.successMessage = null;
          this.errorMessage = 'Error updating profile photo. Please try again.';
        }
      );
    }
  }
}
