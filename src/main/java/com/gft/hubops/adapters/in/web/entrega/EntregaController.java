package com.gft.hubops.adapters.in.web.entrega;

import com.gft.hubops.adapters.in.web.entrega.dto.EntregaRequest;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaResponse;
import com.gft.hubops.application.service.entrega.EntregaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.gft.hubops.adapters.in.web.entrega.dto.AtualizarStatusEntregaRequest;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/clientes/{clienteId}/entregas")
@RequiredArgsConstructor
@Tag(name = "Entregas", description = "Operações de criação, consulta, atualização de status e cancelamento de entregas")
public class EntregaController {

    private final EntregaService entregaService;

    @Operation(summary = "Criar entrega", description = "Cria uma nova entrega baseada em uma cotação existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrega criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente ou cotação não encontrado."),
            @ApiResponse(responseCode = "409", description = "Já existe uma entrega para esta cotação.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaResponse criar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @RequestBody @Valid EntregaRequest request
    ) {
        return entregaService.criar(clienteId, request);
    }

    @Operation(summary = "Atualizar status da entrega", description = "Altera o status de uma entrega existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido ou dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Entrega ou cliente não encontrado."),
            @ApiResponse(responseCode = "409", description = "Entrega concluída ou cancelada não pode ser alterada.")
    })
    @PatchMapping("/{entregaId}/status")
    public EntregaResponse atualizarStatus(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID da entrega deve ser um número positivo.") Long entregaId,
            @RequestBody @Valid AtualizarStatusEntregaRequest request
    ) {
        return entregaService.atualizarStatus(clienteId, entregaId, request);
    }

    @Operation(summary = "Listar entregas do cliente", description = "Retorna todas as entregas de um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de entregas retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping
    public List<EntregaResponse> listarPorCliente(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId
    ) {
        return entregaService.listarPorCliente(clienteId);
    }

    @Operation(summary = "Buscar entrega por ID", description = "Retorna os dados de uma entrega específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega encontrada."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Entrega ou cliente não encontrado.")
    })
    @GetMapping("/{entregaId}")
    public EntregaResponse buscarPorId(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID da entrega deve ser um número positivo.") Long entregaId
    ) {
        return entregaService.buscarPorId(clienteId, entregaId);
    }

    @Operation(summary = "Cancelar entrega", description = "Cancela uma entrega existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega cancelada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Entrega ou cliente não encontrado."),
            @ApiResponse(responseCode = "409", description = "Entrega entregue ou já cancelada não pode ser cancelada.")
    })
    @PatchMapping("/{entregaId}/cancelar")
    public EntregaResponse cancelar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID da entrega deve ser um número positivo.") Long entregaId
    ) {
        return entregaService.cancelar(clienteId, entregaId);
    }

}