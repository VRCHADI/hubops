import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Clientes } from './pages/clientes/clientes';

export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: 'dashboard',
    component: Dashboard
  },
  
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
  path: 'clientes',
  component: Clientes
}

];

