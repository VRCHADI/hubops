package com.gft.hubops.adapters.in.web.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String senha
) {
}