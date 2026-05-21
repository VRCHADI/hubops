package com.gft.hubops.adapters.in.web.cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.gft.hubops.adapters.in.web.cliente.dto.ClienteRequest;
import com.gft.hubops.adapters.in.web.cliente.dto.ClienteResponse;
import com.gft.hubops.application.service.cliente.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações de cadastro, consulta e atualização de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente com nome, e-mail e documento únicos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou faltantes."),
            @ApiResponse(responseCode = "409", description = "E-mail ou documento já cadastrado.")
    })
    public ClienteResponse cadastrar(@RequestBody @Valid ClienteRequest request) {
        return clienteService.cadastrar(request);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista de todos os clientes cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso.")
    })
    public List<ClienteResponse> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "400", description = "ID deve ser um número positivo."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ClienteResponse buscarPorId(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long id
    ) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado."),
            @ApiResponse(responseCode = "409", description = "E-mail ou documento já cadastrado para outro cliente.")
    })
    public ClienteResponse atualizar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long id,
            @RequestBody @Valid ClienteRequest request
    ) {
        return clienteService.atualizar(id, request);
    }

}