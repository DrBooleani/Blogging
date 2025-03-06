import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { ProfileService } from '../../../shared/services/profile.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  imports: [NgIf]
})
export class ProfileComponent implements OnInit {
  userProfile: any = null;
  error: string | null = null;

  constructor(
    private profileService: ProfileService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    const authToken = this.cookieService.get('authToken');
    
    if (authToken) {
      try {
        const decodedToken: any = jwtDecode(authToken);
        const userId = decodedToken.id;

        this.profileService.getUserProfile(userId, authToken).subscribe({
          next: (profile: any) => {
            this.userProfile = profile;
          },
          error: (err: any) => {
            this.error = 'Failed to load profile. Please try again.';
          }
        });
      } catch (error) {
        this.error = 'Error decoding token. You are not authenticated.';
      }
    } else {
      this.error = 'You must be logged in to view your profile.';
    }
  }

  onUpdateProfilePicture() {

  }

  onChangePassword() {

  }
}
