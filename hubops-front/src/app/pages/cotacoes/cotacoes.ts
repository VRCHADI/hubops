import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-cotacoes',
  imports: [RouterLink],
  templateUrl: './cotacoes.html',
  styleUrl: './cotacoes.css',
})
export class Cotacoes {

  constructor(private router: Router) {}

  sair() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}