import { Component, OnInit } from '@angular/core';
import { NavigationLink } from '../../interfaces/navigation-link';
import { NgFor, NgSwitch, NgSwitchCase } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faNewspaper } from '@fortawesome/free-solid-svg-icons/faNewspaper';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faCircleUser } from '@fortawesome/free-solid-svg-icons/faCircleUser';
import { faRightFromBracket } from '@fortawesome/free-solid-svg-icons/faRightFromBracket';

const icons = [
  faHome,
  faNewspaper
];

@Component({
  selector: 'app-header',
  imports: [NgFor, NgSwitch, NgSwitchCase, RouterLink, RouterLinkActive, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  navigationLinks: Array<NavigationLink> = [];
  
  faUser = faUser;
  faUserPlus = faUserPlus;
  faCircleUser = faCircleUser;
  faRightFromBacket = faRightFromBracket;

  ngOnInit(): void {
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

}
