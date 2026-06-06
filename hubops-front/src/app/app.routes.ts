import { Routes } from '@angular/router';

import { Login } from './pages/login/login';
import { Dashboard } from './pages/dashboard/dashboard';
import { Clientes } from './pages/clientes/clientes';
import { Cotacoes } from './pages/cotacoes/cotacoes';
import { Entregas } from './pages/entregas/entregas';

import { MainLayout } from './layouts/main-layout/main-layout';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: Login
  },

  {
    path: '',
    component: MainLayout,
    children: [
      {
        path: 'dashboard',
        component: Dashboard
      },
      {
        path: 'clientes',
        component: Clientes
      },
      {
        path: 'cotacoes',
        component: Cotacoes
      },
      {
        path: 'entregas',
        component: Entregas
      }
    ]
  }
];