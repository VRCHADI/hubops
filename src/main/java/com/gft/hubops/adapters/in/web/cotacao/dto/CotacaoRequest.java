package com.gft.hubops.adapters.in.web.cotacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CotacaoRequest(

        @NotBlank(message = "O CEP de origem é obrigatório.")
        @Pattern(
                regexp = "\\d{8}|\\d{5}-\\d{3}",
                message = "CEP de origem inválido."
        )
        String cepOrigem,

        @NotBlank(message = "O CEP de destino é obrigatório.")
        @Pattern(
                regexp = "\\d{8}|\\d{5}-\\d{3}",
                message = "CEP de destino inválido."
        )
        String cepDestino,

        @NotNull(message = "O peso é obrigatório.")
        @DecimalMin(
                value = "0.1",
                message = "O peso deve ser maior que zero."
        )
        Double pesoKg
) {
}