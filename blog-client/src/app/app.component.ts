import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./shared/navigation/header/header.component";
import { FooterComponent } from "./shared/navigation/footer/footer.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  template: `
    <div class="d-flex flex-column min-vh-100">
      <app-header></app-header>
      <div class="flex-grow-1">
        <router-outlet></router-outlet>
      </div>
      <app-footer></app-footer>
    </div>
  `,
  styles: [],
})
export class AppComponent {}
