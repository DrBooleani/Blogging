import { Component, OnInit } from '@angular/core';
import { NavigationLink } from '../../interfaces/navigation-link';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faNewspaper } from '@fortawesome/free-solid-svg-icons/faNewspaper';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faCircleUser } from '@fortawesome/free-solid-svg-icons/faCircleUser';
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons/faRightFromBracket';
import { AuthStateService } from '../../services/auth-state.service';
import { CommonModule, NgFor, NgIf } from '@angular/common';

const icons = [
  faHome,
  faNewspaper
];

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive, FontAwesomeModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  navigationLinks: Array<NavigationLink> = [];
  
  faUser = faUser;
  faUserPlus = faUserPlus;
  faCircleUser = faCircleUser;
  faRightFromBacket = faRightFromBracket;

  constructor(private authStateService: AuthStateService) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authStateService.getLoginState();
    this.updateNavigationLinks();
  }

  ngAfterContentInit() {
    console.log(this.isLoggedIn);
    
    this.isLoggedIn = this.authStateService.getLoginState();
  }

  updateNavigationLinks(): void {
    this.navigationLinks = [
      {
        label: 'Home',
        icon: icons[0],
        url: '/'
      },
      {
        label: 'Posts',
        icon: icons[1],
        url: '/posts'
      }
    ];
  }

  logout(): void {
    this.authStateService.logout();
    window.location.href = '/';
  }
}
