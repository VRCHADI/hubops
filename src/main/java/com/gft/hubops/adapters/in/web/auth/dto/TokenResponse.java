package com.gft.hubops.adapters.in.web.auth.dto;

public record TokenResponse(
        String mensagem,
        String token
) {
}