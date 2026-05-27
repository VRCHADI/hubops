import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface ClienteRequest {
  nome: string;
  email: string;
  documento: string;
}

export interface ClienteResponse {
  id: number;
  nome: string;
  email: string;
  documento: string;
  criadoEm: string;
}

@Injectable({
  providedIn: 'root',
})
export class Cliente {

  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  listar() {
    return this.http.get<ClienteResponse[]>(this.apiUrl);
  }

  cadastrar(cliente: ClienteRequest) {
    return this.http.post<ClienteResponse>(this.apiUrl, cliente);
  }

}