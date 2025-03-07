import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from '../../../shared/services/profile.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup = new FormGroup({});
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      fullName: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const { fullName, email, password } = this.registerForm.value;
      
      this.isLoading = true;

      this.profileService.createUser(fullName, email, password).subscribe({
        next: (response: any) => {
          this.isLoading = false;
          console.log('User created successfully:', response);
          this.router.navigate(['/auth/login']);
        },
        error: (error: any) => {
          this.isLoading = false;
          this.errorMessage = 'An error occurred while creating the user.';
          console.error('Error:', error);
        }
      });
    } else {
      console.log('Form is not valid');
    }
  }
}
