package com.gft.hubops.adapters.in.web.endereco;

import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoRequest;
import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoResponse;
import com.gft.hubops.application.service.endereco.EnderecoService;
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

@RestController
@RequestMapping("/api/clientes/{clienteId}/enderecos")
@RequiredArgsConstructor
@Tag(name = "Endereços", description = "Operações de cadastro, consulta, atualização e remoção de endereços")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Cadastrar endereço", description = "Cadastra um novo endereço para um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnderecoResponse cadastrar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @RequestBody @Valid EnderecoRequest request
    ) {
        return enderecoService.cadastrar(clienteId, request);
    }

    @Operation(summary = "Listar endereços do cliente", description = "Retorna todos os endereços de um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping
    public List<EnderecoResponse> listarPorCliente(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId
    ) {
        return enderecoService.listarPorCliente(clienteId);
    }

    @Operation(summary = "Buscar endereço por ID", description = "Retorna os dados de um endereço específico de um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Endereço ou cliente não encontrado.")
    })
    @GetMapping("/{enderecoId}")
    public EnderecoResponse buscarPorId(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID do endereço deve ser um número positivo.") Long enderecoId
    ) {
        return enderecoService.buscarPorId(clienteId, enderecoId);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Endereço ou cliente não encontrado.")
    })
    @PutMapping("/{enderecoId}")
    public EnderecoResponse atualizar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID do endereço deve ser um número positivo.") Long enderecoId,
            @RequestBody @Valid EnderecoRequest request
    ) {
        return enderecoService.atualizar(clienteId, enderecoId, request);
    }

    @Operation(summary = "Excluir endereço", description = "Remove um endereço existente de um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Endereço ou cliente não encontrado.")
    })
    @DeleteMapping("/{enderecoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID do endereço deve ser um número positivo.") Long enderecoId
    ) {
        enderecoService.deletar(clienteId, enderecoId);
    }

}
