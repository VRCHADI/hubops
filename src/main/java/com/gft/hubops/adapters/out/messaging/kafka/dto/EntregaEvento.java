package com.gft.hubops.adapters.out.messaging.kafka.dto;

import com.gft.hubops.domain.model.entrega.StatusEntrega;

import java.time.LocalDateTime;

public record EntregaEvento(
        Long entregaId,
        String codigoRastreamento,
        StatusEntrega statusAnterior,
        StatusEntrega status,
        Long cotacaoId,
        Long clienteId,
        String tipoEvento,
        LocalDateTime dataEvento
) {
}