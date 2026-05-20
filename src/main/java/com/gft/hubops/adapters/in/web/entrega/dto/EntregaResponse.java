package com.gft.hubops.adapters.in.web.entrega.dto;

import com.gft.hubops.domain.model.entrega.StatusEntrega;

import java.time.LocalDateTime;

public record EntregaResponse(

        Long id,
        String codigoRastreamento,
        StatusEntrega status,
        LocalDateTime criadaEm,
        Long cotacaoId,
        Long clienteId
) {
}