package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    public final KafkaTemplate<String,EventRequestDTO> kafkaTemplate;

    public EventProducerService(@NotNull KafkaTemplate<String, EventRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addEvent(EventRequestDTO eventRequestDTO){
        kafkaTemplate.send("webhooker-events", eventRequestDTO.userId(), eventRequestDTO)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send event to Kafka: " + ex.getMessage());
                    }
                });
    }
}
