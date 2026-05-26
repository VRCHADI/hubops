import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(email: string, senha: string) {

    return this.http.post(`${this.apiUrl}/login`, {
      email,
      senha
    });

  }

}