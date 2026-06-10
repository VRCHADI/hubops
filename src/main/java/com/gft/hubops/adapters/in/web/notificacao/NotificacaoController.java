package com.gft.hubops.adapters.in.web.notificacao;

import com.gft.hubops.adapters.in.web.notificacao.dto.NotificacaoResponse;
import com.gft.hubops.application.service.notificacao.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
@Tag(
        name = "Notificações",
        description = "Endpoints para consulta e controle de notificações operacionais do HubOps"
)
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @GetMapping
    @Operation(
            summary = "Listar notificações",
            description = "Retorna todas as notificações operacionais cadastradas, ordenadas da mais recente para a mais antiga."
    )
    public List<NotificacaoResponse> listar() {
        return notificacaoService.listar();
    }

    @GetMapping("/nao-lidas/total")
    @Operation(
            summary = "Contar notificações não lidas",
            description = "Retorna o total de notificações ainda não lidas."
    )
    public Map<String, Long> contarNaoLidas() {
        return Map.of("total", notificacaoService.contarNaoLidas());
    }

    @PatchMapping("/{id}/marcar-como-lida")
    @Operation(
            summary = "Marcar notificação como lida",
            description = "Marca uma notificação específica como lida a partir do seu ID."
    )
    public NotificacaoResponse marcarComoLida(@PathVariable Long id) {
        return notificacaoService.marcarComoLida(id);
    }
}