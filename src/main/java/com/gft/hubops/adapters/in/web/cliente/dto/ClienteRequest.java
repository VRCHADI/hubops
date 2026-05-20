package com.gft.hubops.adapters.in.web.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = "O nome não pode conter números ou caracteres especiais."
        )
        String nome,

        @Email(message = "E-mail inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,

        @NotBlank(message = "O documento é obrigatório.")
        String documento
) {
}