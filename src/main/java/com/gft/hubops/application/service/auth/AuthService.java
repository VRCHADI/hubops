package com.gft.hubops.application.service.auth;

import com.gft.hubops.adapters.in.web.auth.dto.CadastroRequest;
import com.gft.hubops.adapters.in.web.auth.dto.LoginRequest;
import com.gft.hubops.adapters.in.web.auth.dto.TokenResponse;
import com.gft.hubops.adapters.out.persistence.usuario.UsuarioRepository;
import com.gft.hubops.config.security.JwtService;
import com.gft.hubops.domain.model.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gft.hubops.adapters.in.web.auth.dto.MensagemResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public MensagemResponse cadastrar(CadastroRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .perfil(request.perfil())
                .criadoEm(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);
        return new MensagemResponse("Usuário cadastrado com sucesso.");
    }

    public TokenResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos."));

        boolean senhaValida = passwordEncoder.matches(request.senha(), usuario.getSenha());

        if (!senhaValida) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new TokenResponse("Login realizado com sucesso.", token);
    }
}