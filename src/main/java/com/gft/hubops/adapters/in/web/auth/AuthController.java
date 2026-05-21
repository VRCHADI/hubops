package com.gft.hubops.adapters.in.web.auth;

import com.gft.hubops.adapters.in.web.auth.dto.CadastroRequest;
import com.gft.hubops.adapters.in.web.auth.dto.LoginRequest;
import com.gft.hubops.adapters.in.web.auth.dto.TokenResponse;
import com.gft.hubops.application.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.gft.hubops.adapters.in.web.auth.dto.MensagemResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Operações de cadastro e autenticação de usuários com JWT")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Cadastrar usuário", description = "Registra um novo usuário no sistema com nome, e-mail, senha e perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado no sistema.")
    })
    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public MensagemResponse cadastrar(@RequestBody @Valid CadastroRequest request) {
        return authService.cadastrar(request);
    }

    @Operation(summary = "Autenticar usuário", description = "Valida as credenciais do usuário e retorna um token JWT para acesso à API.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso. Token JWT retornado."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos.")
    })
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}