import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';

import { Cliente, ClienteResponse } from '../../core/services/cliente';
import { Cotacao, CotacaoResponse } from '../../core/services/cotacao';
import { Entrega, EntregaResponse } from '../../core/services/entrega';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  clientes = signal<ClienteResponse[]>([]);
  cotacoes = signal<CotacaoResponse[]>([]);
  entregas = signal<EntregaResponse[]>([]);
  ultimasEntregas = signal<EntregaResponse[]>([]);

  carregando = signal(false);
  erro = signal('');

  totalClientes = signal(0);
  totalCotacoes = signal(0);
  totalEntregas = signal(0);

  entregasPendentes = signal(0);
  entregasEmTransito = signal(0);
  entregasEntregues = signal(0);
  entregasCanceladas = signal(0);

  percentualPendentes = signal(0);
  percentualEmTransito = signal(0);
  percentualEntregues = signal(0);
  percentualCanceladas = signal(0);

  constructor(
    private clienteService: Cliente,
    private cotacaoService: Cotacao,
    private entregaService: Entrega
  ) {}

  ngOnInit() {
    this.carregarDashboard();
  }

  carregarDashboard() {
    this.carregando.set(true);
    this.erro.set('');

    this.clienteService.listar()
      .subscribe({
        next: (clientes) => {
          this.clientes.set(clientes);
          this.totalClientes.set(clientes.length);

          if (clientes.length === 0) {
            this.carregando.set(false);
            return;
          }

          this.carregarDadosOperacionais(clientes);
        },
        error: (error) => {
          console.error('Erro ao carregar clientes no dashboard:', error);
          this.erro.set('Não foi possível carregar os dados do dashboard.');
          this.carregando.set(false);
        }
      });
  }

  private carregarDadosOperacionais(clientes: ClienteResponse[]) {
    const cotacoesRequests = clientes.map((cliente) =>
      this.cotacaoService.listarPorCliente(cliente.id)
        .pipe(catchError(() => of([] as CotacaoResponse[])))
    );

    const entregasRequests = clientes.map((cliente) =>
      this.entregaService.listarPorCliente(cliente.id)
        .pipe(catchError(() => of([] as EntregaResponse[])))
    );

    forkJoin({
      cotacoesPorCliente: forkJoin(cotacoesRequests),
      entregasPorCliente: forkJoin(entregasRequests)
    })
      .pipe(
        finalize(() => {
          this.carregando.set(false);
        })
      )
      .subscribe({
        next: (response) => {
          const cotacoes = response.cotacoesPorCliente.flat();
          const entregas = response.entregasPorCliente.flat();

          this.cotacoes.set(cotacoes);
          this.entregas.set(entregas);

          this.calcularIndicadores(cotacoes, entregas);
          this.carregarUltimasEntregas(entregas);
        },
        error: (error) => {
          console.error('Erro ao carregar dados operacionais:', error);
          this.erro.set('Não foi possível carregar os indicadores operacionais.');
        }
      });
  }

  private calcularIndicadores(
    cotacoes: CotacaoResponse[],
    entregas: EntregaResponse[]
  ) {
    this.totalCotacoes.set(cotacoes.length);
    this.totalEntregas.set(entregas.length);

    this.entregasPendentes.set(entregas.filter((entrega) => entrega.status === 'PENDENTE').length);
    this.entregasEmTransito.set(entregas.filter((entrega) => entrega.status === 'EM_TRANSITO').length);
    this.entregasEntregues.set(entregas.filter((entrega) => entrega.status === 'ENTREGUE').length);
    this.entregasCanceladas.set(entregas.filter((entrega) => entrega.status === 'CANCELADA').length);

    const totalEntregas = entregas.length || 1;

    this.percentualPendentes.set(Math.round((this.entregasPendentes() / totalEntregas) * 100));
    this.percentualEmTransito.set(Math.round((this.entregasEmTransito() / totalEntregas) * 100));
    this.percentualEntregues.set(Math.round((this.entregasEntregues() / totalEntregas) * 100));
    this.percentualCanceladas.set(Math.round((this.entregasCanceladas() / totalEntregas) * 100));
  }

  private carregarUltimasEntregas(entregas: EntregaResponse[]) {
    const ultimas = [...entregas]
      .sort((a, b) => new Date(b.criadaEm).getTime() - new Date(a.criadaEm).getTime())
      .slice(0, 5);

    this.ultimasEntregas.set(ultimas);
  }
}