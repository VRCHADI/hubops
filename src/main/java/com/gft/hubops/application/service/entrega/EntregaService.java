package com.gft.hubops.application.service.entrega;

import com.gft.hubops.adapters.in.web.entrega.dto.EntregaRequest;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaResponse;
import com.gft.hubops.adapters.out.persistence.cliente.ClienteRepository;
import com.gft.hubops.adapters.out.persistence.cotacao.CotacaoRepository;
import com.gft.hubops.adapters.out.persistence.entrega.EntregaRepository;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import com.gft.hubops.domain.model.cliente.Cliente;
import com.gft.hubops.domain.model.cotacao.Cotacao;
import com.gft.hubops.domain.model.entrega.Entrega;
import com.gft.hubops.domain.model.entrega.StatusEntrega;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.gft.hubops.adapters.in.web.entrega.dto.AtualizarStatusEntregaRequest;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;
    private final CotacaoRepository cotacaoRepository;

    public EntregaResponse criar(Long clienteId, EntregaRequest request) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        Cotacao cotacao = cotacaoRepository.findById(request.cotacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cotação não encontrada."));

        if (!cotacao.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Cotação não encontrada para este cliente.");
        }

        if (entregaRepository.findByCotacaoId(request.cotacaoId()).isPresent()) {
            throw new RuntimeException("Já existe uma entrega para esta cotação.");
        }

        Entrega entrega = Entrega.builder()
                .codigoRastreamento(gerarCodigoRastreamento())
                .status(StatusEntrega.PENDENTE)
                .criadaEm(LocalDateTime.now())
                .cotacao(cotacao)
                .cliente(cliente)
                .build();

        Entrega entregaSalva = entregaRepository.save(entrega);

        return new EntregaResponse(
                entregaSalva.getId(),
                entregaSalva.getCodigoRastreamento(),
                entregaSalva.getStatus(),
                entregaSalva.getCriadaEm(),
                entregaSalva.getCotacao().getId(),
                entregaSalva.getCliente().getId()
        );
    }

    private String gerarCodigoRastreamento() {
        return "HUB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public EntregaResponse atualizarStatus(
            Long clienteId,
            Long entregaId,
            AtualizarStatusEntregaRequest request
    ) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Entrega não encontrada."));

        if (!entrega.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Entrega não encontrada para este cliente.");
        }

        if (entrega.getStatus() == StatusEntrega.ENTREGUE) {
            throw new RuntimeException("Entrega concluída não pode ter status alterado.");
        }

        if (entrega.getStatus() == StatusEntrega.CANCELADA) {
            throw new RuntimeException("Entrega cancelada não pode ter status alterado.");
        }

        entrega.setStatus(request.status());

        Entrega entregaAtualizada = entregaRepository.save(entrega);

        return new EntregaResponse(
                entregaAtualizada.getId(),
                entregaAtualizada.getCodigoRastreamento(),
                entregaAtualizada.getStatus(),
                entregaAtualizada.getCriadaEm(),
                entregaAtualizada.getCotacao().getId(),
                entregaAtualizada.getCliente().getId()
        );
    }

    public List<EntregaResponse> listarPorCliente(Long clienteId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        return entregaRepository.findByClienteId(clienteId)
                .stream()
                .map(entrega -> new EntregaResponse(
                        entrega.getId(),
                        entrega.getCodigoRastreamento(),
                        entrega.getStatus(),
                        entrega.getCriadaEm(),
                        entrega.getCotacao().getId(),
                        entrega.getCliente().getId()
                ))
                .toList();
    }

    public EntregaResponse buscarPorId(Long clienteId, Long entregaId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Entrega não encontrada."));

        if (!entrega.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Entrega não encontrada para este cliente.");
        }

        return new EntregaResponse(
                entrega.getId(),
                entrega.getCodigoRastreamento(),
                entrega.getStatus(),
                entrega.getCriadaEm(),
                entrega.getCotacao().getId(),
                entrega.getCliente().getId()
        );
    }

    public EntregaResponse cancelar(Long clienteId, Long entregaId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Entrega não encontrada."));

        if (!entrega.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Entrega não encontrada para este cliente.");
        }

        if (entrega.getStatus() == StatusEntrega.ENTREGUE) {
            throw new RuntimeException("Entrega entregue não pode ser cancelada.");
        }

        if (entrega.getStatus() == StatusEntrega.CANCELADA) {
            throw new RuntimeException("Entrega já está cancelada.");
        }

        entrega.setStatus(StatusEntrega.CANCELADA);

        Entrega entregaCancelada = entregaRepository.save(entrega);

        return new EntregaResponse(
                entregaCancelada.getId(),
                entregaCancelada.getCodigoRastreamento(),
                entregaCancelada.getStatus(),
                entregaCancelada.getCriadaEm(),
                entregaCancelada.getCotacao().getId(),
                entregaCancelada.getCliente().getId()
        );
    }

}