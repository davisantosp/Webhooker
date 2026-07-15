package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.infra.exceptions.ServiceNotAvailableException;
import jakarta.validation.constraints.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EventService {

    public final KafkaTemplate<String,Object> kafkaTemplate;
    private static final String TOPIC = "webhooks-topic";

    public EventService(@NotNull KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addEvent(EventRequestDTO eventRequestDTO){
        try {
            kafkaTemplate.send(TOPIC, eventRequestDTO.userId(), eventRequestDTO)
                    .get(3, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new ServiceNotAvailableException("Messager temporarily unavailable. Try again later.");
        }
    }
}
