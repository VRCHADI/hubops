import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import {
  Notificacao,
  NotificacaoResponse
} from '../../core/services/notificacao';

@Component({
  selector: 'app-main-layout',
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
})
export class MainLayout implements OnInit {

  notificacoes: NotificacaoResponse[] = [];
  totalNaoLidas = 0;
  notificacoesAbertas = false;
  carregandoNotificacoes = false;

  private atualizandoNotificacoes = false;

  constructor(
    private router: Router,
    private notificacaoService: Notificacao
  ) {}

  ngOnInit() {
    this.carregarNotificacoes();
  }

  sair() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  alternarNotificacoes() {
    this.notificacoesAbertas = !this.notificacoesAbertas;

    if (this.notificacoesAbertas) {
      this.carregarNotificacoes();
    }
  }

  carregarNotificacoes() {
    if (this.atualizandoNotificacoes) {
      return;
    }

    this.atualizandoNotificacoes = true;

    if (this.notificacoes.length === 0) {
      this.carregandoNotificacoes = true;
    }

    this.notificacaoService.listar()
      .pipe(
        finalize(() => {
          this.carregandoNotificacoes = false;
          this.atualizandoNotificacoes = false;
        })
      )
      .subscribe({
        next: (notificacoes) => {
          this.notificacoes = notificacoes;
          this.totalNaoLidas = notificacoes.filter((notificacao) => !notificacao.lida).length;
        },
        error: () => {
          this.notificacoes = [];
          this.totalNaoLidas = 0;
        }
      });
  }

  marcarComoLida(notificacao: NotificacaoResponse) {
    if (notificacao.lida) {
      return;
    }

    this.notificacaoService.marcarComoLida(notificacao.id).subscribe({
      next: (notificacaoAtualizada) => {
        this.notificacoes = this.notificacoes.map((item) =>
          item.id === notificacaoAtualizada.id ? notificacaoAtualizada : item
        );

        this.totalNaoLidas = this.notificacoes.filter((item) => !item.lida).length;
      }
    });
  }

  formatarData(data: string) {
    return new Date(data).toLocaleString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}