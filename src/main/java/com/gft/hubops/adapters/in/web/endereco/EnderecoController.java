package com.gft.hubops.adapters.in.web.endereco;

import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoRequest;
import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoResponse;
import com.gft.hubops.application.service.endereco.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnderecoResponse cadastrar(
            @PathVariable Long clienteId,
            @RequestBody @Valid EnderecoRequest request
    ) {
        return enderecoService.cadastrar(clienteId, request);
    }

    @GetMapping
    public List<EnderecoResponse> listarPorCliente(@PathVariable Long clienteId) {
        return enderecoService.listarPorCliente(clienteId);
    }

    @GetMapping("/{enderecoId}")
    public EnderecoResponse buscarPorId(
            @PathVariable Long clienteId,
            @PathVariable Long enderecoId
    ) {
        return enderecoService.buscarPorId(clienteId, enderecoId);
    }

    @PutMapping("/{enderecoId}")
    public EnderecoResponse atualizar(
            @PathVariable Long clienteId,
            @PathVariable Long enderecoId,
            @RequestBody @Valid EnderecoRequest request
    ) {
        return enderecoService.atualizar(clienteId, enderecoId, request);
    }

    @DeleteMapping("/{enderecoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable Long clienteId,
            @PathVariable Long enderecoId
    ) {
        enderecoService.deletar(clienteId, enderecoId);
    }

}