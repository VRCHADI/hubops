package com.gft.hubops.application.service.entrega;

import com.gft.hubops.adapters.in.web.entrega.dto.AtualizarStatusEntregaRequest;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaPorStatusResponse;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaRequest;
import com.gft.hubops.adapters.in.web.entrega.dto.EntregaResponse;
import com.gft.hubops.adapters.out.messaging.kafka.EntregaKafkaProducer;
import com.gft.hubops.adapters.out.messaging.kafka.dto.EntregaEvento;
import com.gft.hubops.adapters.out.persistence.cliente.ClienteRepository;
import com.gft.hubops.adapters.out.persistence.cotacao.CotacaoRepository;
import com.gft.hubops.adapters.out.persistence.entrega.EntregaJdbcRepository;
import com.gft.hubops.adapters.out.persistence.entrega.EntregaRepository;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import com.gft.hubops.domain.model.cliente.Cliente;
import com.gft.hubops.domain.model.cotacao.Cotacao;
import com.gft.hubops.domain.model.entrega.Entrega;
import com.gft.hubops.domain.model.entrega.StatusEntrega;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;
    private final CotacaoRepository cotacaoRepository;
    private final EntregaKafkaProducer entregaKafkaProducer;
    private final EntregaJdbcRepository entregaJdbcRepository;

    @CacheEvict(value = "relatorioEntregasPorStatus", allEntries = true)
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

        log.info(
                "Entrega criada | entregaId={} | clienteId={} | cotacaoId={} | status={}",
                entregaSalva.getId(),
                entregaSalva.getCliente().getId(),
                entregaSalva.getCotacao().getId(),
                entregaSalva.getStatus()
        );

        entregaKafkaProducer.publicar(new EntregaEvento(
                entregaSalva.getId(),
                entregaSalva.getCodigoRastreamento(),
                null,
                entregaSalva.getStatus(),
                entregaSalva.getCotacao().getId(),
                entregaSalva.getCliente().getId(),
                "ENTREGA_CRIADA",
                LocalDateTime.now()
        ));

        return montarResponse(entregaSalva);
    }

    @CacheEvict(value = "relatorioEntregasPorStatus", allEntries = true)
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

        StatusEntrega statusAnterior = entrega.getStatus();

        entrega.setStatus(request.status());

        Entrega entregaAtualizada = entregaRepository.save(entrega);

        log.info(
                "Status da entrega atualizado | entregaId={} | clienteId={} | statusAnterior={} | statusNovo={}",
                entregaAtualizada.getId(),
                entregaAtualizada.getCliente().getId(),
                statusAnterior,
                entregaAtualizada.getStatus()
        );

        entregaKafkaProducer.publicar(new EntregaEvento(
                entregaAtualizada.getId(),
                entregaAtualizada.getCodigoRastreamento(),
                statusAnterior,
                entregaAtualizada.getStatus(),
                entregaAtualizada.getCotacao().getId(),
                entregaAtualizada.getCliente().getId(),
                "STATUS_ENTREGA_ATUALIZADO",
                LocalDateTime.now()
        ));

        return montarResponse(entregaAtualizada);
    }

    public List<EntregaResponse> listarPorCliente(Long clienteId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        return entregaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::montarResponse)
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

        return montarResponse(entrega);
    }

    @CacheEvict(value = "relatorioEntregasPorStatus", allEntries = true)
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

        StatusEntrega statusAnterior = entrega.getStatus();

        entrega.setStatus(StatusEntrega.CANCELADA);

        Entrega entregaCancelada = entregaRepository.save(entrega);

        log.info(
                "Entrega cancelada | entregaId={} | clienteId={} | cotacaoId={} | statusAnterior={} | statusNovo={}",
                entregaCancelada.getId(),
                entregaCancelada.getCliente().getId(),
                entregaCancelada.getCotacao().getId(),
                statusAnterior,
                entregaCancelada.getStatus()
        );

        entregaKafkaProducer.publicar(new EntregaEvento(
                entregaCancelada.getId(),
                entregaCancelada.getCodigoRastreamento(),
                statusAnterior,
                entregaCancelada.getStatus(),
                entregaCancelada.getCotacao().getId(),
                entregaCancelada.getCliente().getId(),
                "ENTREGA_CANCELADA",
                LocalDateTime.now()
        ));

        return montarResponse(entregaCancelada);
    }

    @Cacheable("relatorioEntregasPorStatus")
    public List<EntregaPorStatusResponse> contarEntregasPorStatus() {
        return entregaJdbcRepository.contarEntregasPorStatus();
    }

    private String gerarCodigoRastreamento() {
        return "HUB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private EntregaResponse montarResponse(Entrega entrega) {
        return new EntregaResponse(
                entrega.getId(),
                entrega.getCodigoRastreamento(),
                entrega.getStatus(),
                entrega.getCriadaEm(),
                entrega.getCotacao().getId(),
                entrega.getCliente().getId()
        );
    }
}