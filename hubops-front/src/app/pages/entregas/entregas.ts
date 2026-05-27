import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-entregas',
  imports: [RouterLink],
  templateUrl: './entregas.html',
  styleUrl: './entregas.css',
})
export class Entregas {

  constructor(private router: Router) {}

  sair() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}