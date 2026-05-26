import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cliente, ClienteResponse } from '../../core/services/cliente';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
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
          console.log(response);
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