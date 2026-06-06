import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface CotacaoRequest {
  cepOrigem: string;
  cepDestino: string;
  pesoKg: number | null;
}

export interface CotacaoResponse {
  id: number;
  cepOrigem: string;
  cepDestino: string;
  pesoKg: number;
  valorFrete: number;
  prazoDias: number;
  status: string;
  criadaEm: string;
}

@Injectable({
  providedIn: 'root',
})
export class Cotacao {

  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  listarPorCliente(clienteId: number) {
    return this.http.get<CotacaoResponse[]>(`${this.apiUrl}/${clienteId}/cotacoes`);
  }

  gerar(clienteId: number, cotacao: CotacaoRequest) {
    return this.http.post<CotacaoResponse>(`${this.apiUrl}/${clienteId}/cotacoes`, cotacao);
  }

  cancelar(clienteId: number, cotacaoId: number) {
    return this.http.patch<CotacaoResponse>(`${this.apiUrl}/${clienteId}/cotacoes/${cotacaoId}/cancelar`, {});
  }

}