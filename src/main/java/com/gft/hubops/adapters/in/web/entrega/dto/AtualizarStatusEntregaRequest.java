package com.gft.hubops.adapters.in.web.entrega.dto;

import com.gft.hubops.domain.model.entrega.StatusEntrega;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusEntregaRequest(

        @NotNull(message = "O status é obrigatório.")
        StatusEntrega status
) {
}