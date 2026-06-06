import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef, Component, signal } from '@angular/core';

import {
  Cotacao,
  CotacaoRequest,
  CotacaoResponse
} from '../../core/services/cotacao';

@Component({
  selector: 'app-cotacoes',
  imports: [CommonModule, FormsModule],
  templateUrl: './cotacoes.html',
  styleUrl: './cotacoes.css',
})
export class Cotacoes {

  clienteId: number | null = null;

  cotacoes = signal<CotacaoResponse[]>([]);
  carregando = signal(false);
  erro = signal('');
  sucesso = signal('');

  novaCotacao: CotacaoRequest = {
    cepOrigem: '',
    cepDestino: '',
    pesoKg: null
  };

  constructor(
    private cotacaoService: Cotacao,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  carregarCotacoes() {
    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente para buscar as cotações.');
      return;
    }

    this.carregando.set(true);
    this.erro.set('');

    this.cotacaoService.listarPorCliente(this.clienteId)
      .subscribe({
        next: (response) => {
          this.cotacoes.set(response);
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
        },
        error: (error) => {
          console.error('Erro ao carregar cotações:', error);
          this.erro.set('Não foi possível carregar as cotações.');
          this.carregando.set(false);
          this.changeDetectorRef.detectChanges();
        }
      });
  }

  gerarCotacao() {
  this.sucesso.set('');
  this.erro.set('');

  if (!this.clienteId) {
    this.erro.set('Informe o ID do cliente antes de gerar a cotação.');
    return;
  }

  this.cotacaoService.gerar(this.clienteId, this.novaCotacao)
    .subscribe({
      next: () => {
        this.sucesso.set('Cotação gerada com sucesso.');

        this.novaCotacao = {
          cepOrigem: '',
          cepDestino: '',
          pesoKg: null
        };

        this.carregarCotacoes();
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        console.error('Erro ao gerar cotação:', error);
        this.erro.set('Não foi possível gerar a cotação.');
        this.changeDetectorRef.detectChanges();
      }
    });
}

  cancelarCotacao(cotacaoId: number) {
    if (!this.clienteId) {
      this.erro.set('Informe o ID do cliente antes de cancelar a cotação.');
      return;
    }

    this.cotacaoService.cancelar(this.clienteId, cotacaoId)
      .subscribe({
        next: () => {
          this.carregarCotacoes();
        },
        error: (error) => {
          console.error('Erro ao cancelar cotação:', error);
          this.erro.set('Não foi possível cancelar a cotação.');
          this.changeDetectorRef.detectChanges();
        }
      });
  }
}