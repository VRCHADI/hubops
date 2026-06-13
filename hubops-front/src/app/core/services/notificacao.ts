import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface NotificacaoResponse {
  id: number;
  titulo: string;
  mensagem: string;
  tipo: string;
  lida: boolean;
  criadaEm: string;
  entregaId: number;
  clienteId: number;
  codigoRastreamento: string;
}

export interface TotalNotificacoesNaoLidasResponse {
  total: number;
}

@Injectable({
  providedIn: 'root',
})
export class Notificacao {

  private apiUrl = 'http://localhost:8080/api/notificacoes';

  constructor(private http: HttpClient) {}

  listar() {
    return this.http.get<NotificacaoResponse[]>(this.apiUrl);
  }

  contarNaoLidas() {
    return this.http.get<TotalNotificacoesNaoLidasResponse>(`${this.apiUrl}/nao-lidas/total`);
  }

  marcarComoLida(id: number) {
    return this.http.patch<NotificacaoResponse>(`${this.apiUrl}/${id}/marcar-como-lida`, {});
  }
}