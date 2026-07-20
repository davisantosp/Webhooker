package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.domain.entities.Rule;
import com.davisantosp.Webhooker.repositories.RuleRepository;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.Map;

@Component
public class EventConsumer {

    private final RuleRepository ruleRepository;
    private final RestClient restClient;

    public EventConsumer(RuleRepository ruleRepository, RestClient restClient) {
        this.ruleRepository = ruleRepository;
        this.restClient = restClient;
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
            this.triggerWebhook(rule, eventRequest.payload());
        }
    }

    // It will be changed to a separate module later
    public void triggerWebhook(Rule rule, Map<String, Object> payload){
        try{
            restClient.post()
                    .uri(rule.getUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Webhook-Secret", rule.getSharedSecret())
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            System.out.println("Webhook successfully sent to " + rule.getUrl());
            // In the future, save the logs on the DB for user
        }
        catch (RestClientResponseException e) {
            int statusCode = e.getStatusCode().value();
            String responseBody = e.getResponseBodyAsString();

            System.err.println("Server http resposne " + statusCode);
            System.err.println("Error body: " + responseBody);

        } catch (Exception e) {
            System.err.println("Failed to connect in the url: " + rule.getUrl());
            System.err.println(e.getMessage());
        }
    }
}
