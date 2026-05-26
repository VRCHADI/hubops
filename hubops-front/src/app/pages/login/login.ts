import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../core/services/auth';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  email = '';

  senha = '';

  constructor(
    private authService: Auth,
    private router: Router
  ) {}

  entrar() {
    this.authService.login(this.email, this.senha)
      .subscribe({
        next: (response: any) => {
          localStorage.setItem('token', response.token);
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

}