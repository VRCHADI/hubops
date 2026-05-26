import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Cliente, ClienteResponse } from '../../core/services/cliente';

@Component({
  selector: 'app-clientes',
  imports: [CommonModule],
  templateUrl: './clientes.html',
  styleUrl: './clientes.css',
})
export class Clientes implements OnInit {

  clientes: ClienteResponse[] = [];

  constructor(private clienteService: Cliente) {}

  ngOnInit() {
    this.carregarClientes();
  }

  carregarClientes() {
    this.clienteService.listar()
      .subscribe({
        next: (response) => {
  console.log(response);
  this.clientes = response;
},
       error: (error) => {
  console.error('Erro ao carregar clientes:', error);
}
      });
  }

}