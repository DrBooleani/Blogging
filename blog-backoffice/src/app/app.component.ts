import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { HeaderComponent } from "./shared/navigation/header/header.component";

@Component({
  selector: "app-root",
  imports: [RouterOutlet, HeaderComponent],
  template: `
    <div class="d-flex">
      <app-header></app-header>
      <div class="content flex-grow-1 p-3">
        <router-outlet></router-outlet>
      </div>
    </div>
  `,
  styles: [
    `
      .content {
        margin-top: 5px;
        margin-left: 100px;
        transition: margin-left 0.3s ease-in-out;
      }

      @media (max-width: 768px) {
        .content {
          margin-left: 0;
        }
      }
    `
  ]
})
export class AppComponent {}
