import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface EntregaRequest {
  cotacaoId: number | null;
}

export interface AtualizarStatusEntregaRequest {
  status: string;
}

export interface EntregaResponse {
  id: number;
  codigoRastreamento: string;
  status: string;
  criadaEm: string;
  cotacaoId: number;
  clienteId: number;
}

export interface EntregaPorStatusResponse {
  status: string;
  quantidade: number;
}

@Injectable({
  providedIn: 'root',
})
export class Entrega {

  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  listarPorCliente(clienteId: number) {
    return this.http.get<EntregaResponse[]>(`${this.apiUrl}/${clienteId}/entregas`);
  }

  criar(clienteId: number, entrega: EntregaRequest) {
    return this.http.post<EntregaResponse>(`${this.apiUrl}/${clienteId}/entregas`, entrega);
  }

  atualizarStatus(clienteId: number, entregaId: number, request: AtualizarStatusEntregaRequest) {
    return this.http.patch<EntregaResponse>(`${this.apiUrl}/${clienteId}/entregas/${entregaId}/status`, request);
  }

  cancelar(clienteId: number, entregaId: number) {
    return this.http.patch<EntregaResponse>(`${this.apiUrl}/${clienteId}/entregas/${entregaId}/cancelar`, {});
  }

  contarPorStatus(clienteId: number) {
    return this.http.get<EntregaPorStatusResponse[]>(`${this.apiUrl}/${clienteId}/entregas/relatorios/por-status`);
  }

}