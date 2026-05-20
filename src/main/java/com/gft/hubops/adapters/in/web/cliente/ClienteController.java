package com.gft.hubops.adapters.in.web.cliente;

import com.gft.hubops.adapters.in.web.cliente.dto.ClienteRequest;
import com.gft.hubops.adapters.in.web.cliente.dto.ClienteResponse;
import com.gft.hubops.application.service.cliente.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse cadastrar(@RequestBody @Valid ClienteRequest request) {
        return clienteService.cadastrar(request);
    }

    @GetMapping
    public List<ClienteResponse> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequest request
    ) {
        return clienteService.atualizar(id, request);
    }

}