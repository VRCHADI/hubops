package com.gft.hubops.adapters.in.messaging.kafka;

import com.gft.hubops.adapters.out.messaging.kafka.dto.EntregaEvento;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EntregaKafkaConsumer {

    @KafkaListener(
            topics = "hubops.entregas",
            groupId = "hubops-group"
    )
    public void consumir(EntregaEvento evento) {
        System.out.println("Evento de entrega recebido: " + evento);
    }
}