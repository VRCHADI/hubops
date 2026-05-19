package com.gft.hubops.adapters.in.web.auth.dto;

import com.gft.hubops.domain.model.usuario.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroRequest(

        @NotBlank
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = "O nome não pode conter números ou caracteres especiais."
        )
        String nome,

        @Email(message = "E-mail inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha,

        @NotNull(message = "O perfil é obrigatório.")
        PerfilUsuario perfil
) {
}