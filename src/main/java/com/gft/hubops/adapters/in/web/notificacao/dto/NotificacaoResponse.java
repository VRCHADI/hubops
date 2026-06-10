package com.gft.hubops.adapters.in.web.notificacao.dto;

import com.gft.hubops.domain.model.notificacao.TipoNotificacao;

import java.time.LocalDateTime;

public record NotificacaoResponse(
        Long id,
        String titulo,
        String mensagem,
        TipoNotificacao tipo,
        Boolean lida,
        LocalDateTime criadaEm,
        Long entregaId,
        Long clienteId,
        String codigoRastreamento
) {
}