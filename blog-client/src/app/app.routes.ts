import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'posts',
    loadChildren: () => import('./pages/post/post.routes').then(r => r.POST_ROUTES)
  },
  {
    path: 'auth',
    loadChildren: () => import('./pages/auth/auth.routes').then(r => r.AUTH_ROUTES)
  }
];
