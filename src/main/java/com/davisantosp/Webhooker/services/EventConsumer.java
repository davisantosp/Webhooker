package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.domain.entities.Rule;
import com.davisantosp.Webhooker.repositories.RuleRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventConsumer {

    private final RuleRepository ruleRepository;
    private final WebhookDispatcherService webhookDispatcherService;

    public EventConsumer(RuleRepository ruleRepository, WebhookDispatcherService webhookDispatcherService) {
        this.ruleRepository = ruleRepository;
        this.webhookDispatcherService = webhookDispatcherService;
    }

    @KafkaListener(
            topics = "webhooker-events",
            groupId = "webhooker-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(EventRequestDTO eventRequest){
        List<Rule> rulesToTrigger = ruleRepository.findByUserIdAndEventTypeAndActiveTrue(
                eventRequest.userId(), eventRequest.eventType()
        );

        for(Rule rule : rulesToTrigger){
            webhookDispatcherService.dispatchAsync(rule, eventRequest.payload());
        }
    }
}
