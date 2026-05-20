package com.gft.hubops.adapters.in.web.cliente.dto;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String documento,
        LocalDateTime criadoEm
) {
}