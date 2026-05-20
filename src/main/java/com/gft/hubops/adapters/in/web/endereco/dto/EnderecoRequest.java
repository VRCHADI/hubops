package com.gft.hubops.adapters.in.web.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequest(

        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(
                regexp = "\\d{8}|\\d{5}-\\d{3}",
                message = "O CEP deve estar no formato 00000000 ou 00000-000."
        )
        String cep,

        @NotBlank(message = "O número é obrigatório.")
        String numero,

        String complemento
) {
}