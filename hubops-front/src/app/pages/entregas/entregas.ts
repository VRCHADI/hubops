import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef, Component, signal } from '@angular/core';

import {
  Entrega,
  EntregaRequest,
  EntregaResponse
} from '../../core/services/entrega';

@Component({
  selector: 'app-entregas',
  imports: [CommonModule, FormsModule],
  templateUrl: './entregas.html',
  styleUrl: './entregas.css',
})
export class Entregas {

  clienteId: number | null = null;

  entregas = signal<EntregaResponse[]>([]);
  statusSelecionado: Record<number, string> = {};

  carregando = signal(false);
  erro = signal('');
  sucesso = signal('');

  novaEntrega: EntregaRequest = {
    cotacaoId: null
  };

  statusDisponiveis = [
    'PENDENTE',
    'EM_PREPARACAO',
    'EM_TRANSITO',
    'ENTREGUE',
    'CANCELADA'
  ];

  constructor(
    private entregaService: Entrega,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  carregarEntregas() {
    this.sucesso.set('');

    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente para buscar as entregas.');
      return;
    }

    this.carregando.set(true);
    this.erro.set('');

    this.entregaService.listarPorCliente(this.clienteId)
      .subscribe({
        next: (response) => {
          this.entregas.set(response);
          this.prepararStatusSelecionado(response);
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Erro ao carregar entregas:', error);
          this.erro.set('Não foi possível carregar as entregas.');
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
        }
      });
  }

  criarEntrega() {
    this.sucesso.set('');
    this.erro.set('');

    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente antes de criar a entrega.');
      return;
    }

    if (!this.novaEntrega.cotacaoId) {
      this.erro.set('Informe o ID da cotação antes de criar a entrega.');
      return;
    }

    this.entregaService.criar(this.clienteId, this.novaEntrega)
      .subscribe({
        next: () => {
          this.sucesso.set('Entrega criada com sucesso.');

          this.novaEntrega = {
            cotacaoId: null
          };

          this.carregarEntregas();
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Erro ao criar entrega:', error);
          this.erro.set('Não foi possível criar a entrega.');
          this.changeDetectorRef.detectChanges();
        }
      });
  }

  atualizarStatus(entrega: EntregaResponse) {
    this.sucesso.set('');
    this.erro.set('');

    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente antes de atualizar a entrega.');
      return;
    }

    const novoStatus = this.statusSelecionado[entrega.id];

    if (!novoStatus) {
      this.erro.set('Selecione um status válido.');
      return;
    }

    this.entregaService.atualizarStatus(this.clienteId, entrega.id, {
      status: novoStatus
    }).subscribe({
      next: () => {
        this.sucesso.set('Status da entrega atualizado com sucesso.');
        this.carregarEntregas();
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        console.error('Erro ao atualizar status da entrega:', error);
        this.erro.set('Não foi possível atualizar o status da entrega.');
        this.changeDetectorRef.detectChanges();
      }
    });
  }

  cancelarEntrega(entregaId: number) {
    this.sucesso.set('');
    this.erro.set('');

    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente antes de cancelar a entrega.');
      return;
    }

    this.entregaService.cancelar(this.clienteId, entregaId)
      .subscribe({
        next: () => {
          this.sucesso.set('Entrega cancelada com sucesso.');
          this.carregarEntregas();
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Erro ao cancelar entrega:', error);
          this.erro.set('Não foi possível cancelar a entrega.');
          this.changeDetectorRef.detectChanges();
        }
      });
  }

  podeAlterar(entrega: EntregaResponse) {
    return entrega.status !== 'ENTREGUE' && entrega.status !== 'CANCELADA';
  }

  getStatusClass(status: string) {
  switch (status) {
    case 'PENDENTE':
      return 'status-pendente';

    case 'EM_PREPARACAO':
      return 'status-preparacao';

    case 'EM_TRANSITO':
      return 'status-transito';

    case 'ENTREGUE':
      return 'status-entregue';

    case 'CANCELADA':
      return 'status-cancelada';

    default:
      return '';
  }
}

  private prepararStatusSelecionado(entregas: EntregaResponse[]) {
    this.statusSelecionado = {};

    entregas.forEach((entrega) => {
      this.statusSelecionado[entrega.id] = entrega.status;
    });
  }
}