import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Cliente, ClienteResponse } from '../../core/services/cliente';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  clientes: ClienteResponse[] = [];

  constructor(
    private router: Router,
    private clienteService: Cliente
  ) {}

  ngOnInit() {
    this.carregarClientes();
  }

  carregarClientes() {
  this.clienteService.listar()
    .subscribe({
      next: (response) => {
        this.clientes = response;
      },
      error: (error) => {
        console.error(error);
      }
    });
}

  sair() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}