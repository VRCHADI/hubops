package com.gft.hubops.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HubOps API",
                version = "1.0.0",
                description = "API para hub de operações/logística"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Token JWT para autenticação. Obtenha em /api/auth/login"
)
public class SwaggerConfig {
    // Configuração com suporte a autenticação Bearer JWT.
    // O Swagger UI exibe um botão "Authorize" para inserir o token.
    // Endpoints protegidos incluem o token automaticamente nos headers.
}