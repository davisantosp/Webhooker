package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(
            topics = "webhooker-events",
            groupId = "webhooker-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(EventRequestDTO eventRequest){
        System.out.println("\n📥 [KAFKA CONSUMER] Novo evento capturado no polling!");
        System.out.println("⚡ Tipo de Evento: " + eventRequest.eventType());
        System.out.println("👤 Usuário Dono:   " + eventRequest.userId());
        System.out.println("📦 Payload Útil:   " + eventRequest.payload());
        System.out.println("===================================================\n");
    }
}
