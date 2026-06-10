package com.gft.hubops.adapters.in.messaging.kafka;

import com.gft.hubops.adapters.out.messaging.kafka.dto.EntregaEvento;
import com.gft.hubops.application.service.notificacao.NotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntregaKafkaConsumer {

    private final NotificacaoService notificacaoService;

    @KafkaListener(
            topics = "hubops.entregas",
            groupId = "hubops-group"
    )
    public void consumir(EntregaEvento evento) {

        log.info("Evento de entrega recebido | evento={}", evento);

        if (!"STATUS_ENTREGA_ATUALIZADO".equals(evento.tipoEvento())) {
            log.info("Evento ignorado para notificação | tipoEvento={}", evento.tipoEvento());
            return;
        }

        notificacaoService.criarNotificacaoStatusEntregaAtualizado(evento);
    }
}