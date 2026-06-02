package com.gft.hubops.adapters.in.web.entrega.dto;

public record EntregaPorStatusResponse(
        String status,
        Long quantidade
) {
}