package com.gft.hubops.adapters.in.web.entrega.dto;

import jakarta.validation.constraints.NotNull;

public record EntregaRequest(

        @NotNull(message = "A cotação é obrigatória.")
        Long cotacaoId
) {
}