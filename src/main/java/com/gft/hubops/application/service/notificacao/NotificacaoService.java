package com.gft.hubops.application.service.notificacao;

import com.gft.hubops.adapters.in.web.notificacao.dto.NotificacaoResponse;
import com.gft.hubops.adapters.out.messaging.kafka.dto.EntregaEvento;
import com.gft.hubops.adapters.out.persistence.notificacao.NotificacaoRepository;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import com.gft.hubops.domain.model.notificacao.Notificacao;
import com.gft.hubops.domain.model.notificacao.TipoNotificacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public void criarNotificacaoStatusEntregaAtualizado(EntregaEvento evento) {

        Notificacao notificacao = Notificacao.builder()
                .titulo("Status da entrega atualizado")
                .mensagem("A entrega " + evento.codigoRastreamento() + " mudou de "
                        + evento.statusAnterior() + " para " + evento.status() + ".")
                .tipo(TipoNotificacao.STATUS_ENTREGA_ATUALIZADO)
                .lida(false)
                .criadaEm(LocalDateTime.now())
                .entregaId(evento.entregaId())
                .clienteId(evento.clienteId())
                .codigoRastreamento(evento.codigoRastreamento())
                .build();

        Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);

        log.info(
                "Notificação criada | notificacaoId={} | entregaId={} | clienteId={} | statusAnterior={} | statusNovo={}",
                notificacaoSalva.getId(),
                notificacaoSalva.getEntregaId(),
                notificacaoSalva.getClienteId(),
                evento.statusAnterior(),
                evento.status()
        );
    }

    public List<NotificacaoResponse> listar() {
        return notificacaoRepository.findAllByOrderByCriadaEmDesc()
                .stream()
                .map(this::montarResponse)
                .toList();
    }

    public long contarNaoLidas() {
        return notificacaoRepository.countByLidaFalse();
    }

    public NotificacaoResponse marcarComoLida(Long id) {

        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Notificação não encontrada."));

        notificacao.setLida(true);

        Notificacao notificacaoAtualizada = notificacaoRepository.save(notificacao);

        log.info("Notificação marcada como lida | notificacaoId={}", notificacaoAtualizada.getId());

        return montarResponse(notificacaoAtualizada);
    }

    private NotificacaoResponse montarResponse(Notificacao notificacao) {
        return new NotificacaoResponse(
                notificacao.getId(),
                notificacao.getTitulo(),
                notificacao.getMensagem(),
                notificacao.getTipo(),
                notificacao.getLida(),
                notificacao.getCriadaEm(),
                notificacao.getEntregaId(),
                notificacao.getClienteId(),
                notificacao.getCodigoRastreamento()
        );
    }
}