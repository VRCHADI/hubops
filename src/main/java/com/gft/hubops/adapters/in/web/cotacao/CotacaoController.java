package com.gft.hubops.adapters.in.web.cotacao;

import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoRequest;
import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoResponse;
import com.gft.hubops.application.service.cotacao.CotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/cotacoes")
@RequiredArgsConstructor
public class CotacaoController {

    private final CotacaoService cotacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CotacaoResponse gerar(
            @PathVariable Long clienteId,
            @RequestBody @Valid CotacaoRequest request
    ) {
        return cotacaoService.gerar(clienteId, request);
    }

    @GetMapping
    public List<CotacaoResponse> listarPorCliente(
            @PathVariable Long clienteId
    ) {
        return cotacaoService.listarPorCliente(clienteId);
    }

    @GetMapping("/{cotacaoId}")
    public CotacaoResponse buscarPorId(
            @PathVariable Long clienteId,
            @PathVariable Long cotacaoId
    ) {
        return cotacaoService.buscarPorId(clienteId, cotacaoId);
    }

    @PatchMapping("/{cotacaoId}/cancelar")
    public CotacaoResponse cancelar(
            @PathVariable Long clienteId,
            @PathVariable Long cotacaoId
    ) {
        return cotacaoService.cancelar(clienteId, cotacaoId);
    }

}