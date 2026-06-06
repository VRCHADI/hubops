import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';

import {
  Cliente,
  ClienteRequest,
  ClienteResponse
} from '../../core/services/cliente';

@Component({
  selector: 'app-clientes',
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.html',
  styleUrl: './clientes.css',
})
export class Clientes implements OnInit {

  clientes = signal<ClienteResponse[]>([]);
  carregando = signal(false);
  erro = signal('');

  novoCliente: ClienteRequest = {
    nome: '',
    email: '',
    documento: ''
  };

  constructor(
    private clienteService: Cliente,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.carregarClientes();
  }

  carregarClientes() {
    this.carregando.set(true);
    this.erro.set('');

    this.clienteService.listar()
      .subscribe({
        next: (response) => {
          this.clientes.set(response);
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Erro ao carregar clientes:', error);
          this.erro.set('Não foi possível carregar os clientes.');
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
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
}