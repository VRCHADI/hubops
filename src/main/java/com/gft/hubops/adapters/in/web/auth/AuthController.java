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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public MensagemResponse cadastrar(@RequestBody @Valid CadastroRequest request) {
        return authService.cadastrar(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}