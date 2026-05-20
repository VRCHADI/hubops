package com.gft.hubops.adapters.in.web.entrega;

import com.gft.hubops.adapters.in.web.entrega.dto.EntregaRequest;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaResponse;
import com.gft.hubops.application.service.entrega.EntregaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.gft.hubops.adapters.in.web.entrega.dto.AtualizarStatusEntregaRequest;
import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaResponse criar(
            @PathVariable Long clienteId,
            @RequestBody @Valid EntregaRequest request
    ) {
        return entregaService.criar(clienteId, request);
    }

    @PatchMapping("/{entregaId}/status")
    public EntregaResponse atualizarStatus(
            @PathVariable Long clienteId,
            @PathVariable Long entregaId,
            @RequestBody @Valid AtualizarStatusEntregaRequest request
    ) {
        return entregaService.atualizarStatus(clienteId, entregaId, request);
    }

    @GetMapping
    public List<EntregaResponse> listarPorCliente(
            @PathVariable Long clienteId
    ) {
        return entregaService.listarPorCliente(clienteId);
    }

    @GetMapping("/{entregaId}")
    public EntregaResponse buscarPorId(
            @PathVariable Long clienteId,
            @PathVariable Long entregaId
    ) {
        return entregaService.buscarPorId(clienteId, entregaId);
    }

    @PatchMapping("/{entregaId}/cancelar")
    public EntregaResponse cancelar(
            @PathVariable Long clienteId,
            @PathVariable Long entregaId
    ) {
        return entregaService.cancelar(clienteId, entregaId);
    }

}