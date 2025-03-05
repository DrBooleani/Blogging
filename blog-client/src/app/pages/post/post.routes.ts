import { Routes } from "@angular/router";
import { PostListComponent } from "./post-list/post-list.component";
import { PostDetailsComponent } from "./post-details/post-details.component";

export const POST_ROUTES: Routes = [
  {
    path: '',
    component: PostListComponent
  },
  {
    path: ':id',
    component: PostDetailsComponent
  }
];