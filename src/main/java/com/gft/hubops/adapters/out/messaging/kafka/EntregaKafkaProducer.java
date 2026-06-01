package com.gft.hubops.adapters.out.messaging.kafka;

import com.gft.hubops.adapters.out.messaging.kafka.dto.EntregaEvento;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntregaKafkaProducer {

    private static final String TOPICO_ENTREGAS = "hubops.entregas";

    private final KafkaTemplate<String, EntregaEvento> kafkaTemplate;

    public void publicar(EntregaEvento evento) {

        System.out.println("Publicando evento de entrega no Kafka: " + evento);

        kafkaTemplate.send(
                TOPICO_ENTREGAS,
                evento.entregaId().toString(),
                evento
        );
    }
}