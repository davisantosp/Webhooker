package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.infra.exceptions.ServiceNotAvailableException;
import jakarta.validation.constraints.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EventProducerService {

    public final KafkaTemplate<String,EventRequestDTO> kafkaTemplate;

    public EventProducerService(@NotNull KafkaTemplate<String, EventRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addEvent(EventRequestDTO eventRequestDTO){
        try {
            kafkaTemplate.send("webhooker-events", eventRequestDTO.userId(), eventRequestDTO)
                    .get(3, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new ServiceNotAvailableException("Messager temporarily unavailable. Try again later.");
        }
    }
}
