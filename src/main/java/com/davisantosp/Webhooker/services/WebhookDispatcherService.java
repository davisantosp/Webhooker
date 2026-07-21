package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.entities.Rule;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Service
public class WebhookDispatcherService {

    private final RestClient restClient;

    public WebhookDispatcherService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Async
    public void dispatchAsync(Rule rule, Map<String, Object> payload){
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
