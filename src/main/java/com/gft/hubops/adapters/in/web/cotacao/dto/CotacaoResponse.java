package com.gft.hubops.adapters.in.web.cotacao.dto;

import com.gft.hubops.domain.model.cotacao.StatusCotacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CotacaoResponse(

        Long id,
        String cepOrigem,
        String cepDestino,
        Double pesoKg,
        BigDecimal valorFrete,
        Integer prazoDias,
        StatusCotacao status,
        LocalDateTime criadaEm
) {
}