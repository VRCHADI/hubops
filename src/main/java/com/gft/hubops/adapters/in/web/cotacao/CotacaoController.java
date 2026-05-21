package com.gft.hubops.adapters.in.web.cotacao;

import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoRequest;
import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoResponse;
import com.gft.hubops.application.service.cotacao.CotacaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/clientes/{clienteId}/cotacoes")
@RequiredArgsConstructor
@Tag(name = "Cotações", description = "Operações de geração, consulta e cancelamento de cotações de frete")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    @Operation(summary = "Gerar cotação", description = "Cria uma nova cotação de frete baseado no CEP de origem, destino e peso.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cotação gerada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CotacaoResponse gerar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @RequestBody @Valid CotacaoRequest request
    ) {
        return cotacaoService.gerar(clienteId, request);
    }

    @Operation(summary = "Listar cotações do cliente", description = "Retorna todas as cotações de um cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cotações retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping
    public List<CotacaoResponse> listarPorCliente(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId
    ) {
        return cotacaoService.listarPorCliente(clienteId);
    }

    @Operation(summary = "Buscar cotação por ID", description = "Retorna os dados de uma cotação específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotação encontrada."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cotação ou cliente não encontrado.")
    })
    @GetMapping("/{cotacaoId}")
    public CotacaoResponse buscarPorId(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID da cotação deve ser um número positivo.") Long cotacaoId
    ) {
        return cotacaoService.buscarPorId(clienteId, cotacaoId);
    }

    @Operation(summary = "Cancelar cotação", description = "Cancela uma cotação existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotação cancelada com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cotação ou cliente não encontrado."),
            @ApiResponse(responseCode = "409", description = "Cotação já está cancelada.")
    })
    @PatchMapping("/{cotacaoId}/cancelar")
    public CotacaoResponse cancelar(
            @PathVariable @Positive(message = "O ID do cliente deve ser um número positivo.") Long clienteId,
            @PathVariable @Positive(message = "O ID da cotação deve ser um número positivo.") Long cotacaoId
    ) {
        return cotacaoService.cancelar(clienteId, cotacaoId);
    }

}