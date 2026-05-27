import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {
  Cliente,
  ClienteRequest,
  ClienteResponse
} from '../../core/services/cliente';
@Component({
  selector: 'app-clientes',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './clientes.html',
  styleUrl: './clientes.css',
})
export class Clientes implements OnInit {

clientes: ClienteResponse[] = [];
novoCliente: ClienteRequest = {
  nome: '',
  email: '',
  documento: ''
};

  constructor(
  private clienteService: Cliente,
  private router: Router
) {}

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


  cadastrarCliente() {

  this.clienteService.cadastrar(this.novoCliente)
    .subscribe({
      next: () => {
        this.novoCliente = {
          nome: '',
          email: '',
          documento: ''
        };

        this.carregarClientes();
      },
      error: (error) => {
        console.error('Erro ao cadastrar cliente:', error);
      }
    });
}

sair() {
  localStorage.removeItem('token');
  this.router.navigate(['/login']);
}

}