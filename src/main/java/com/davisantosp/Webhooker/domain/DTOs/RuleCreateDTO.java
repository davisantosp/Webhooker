package com.davisantosp.Webhooker.domain.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class RuleCreateDTO {

    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String userId;
    @URL
    @NotBlank
    @Size(max = 2048)
    private String url;
    @NotBlank
    private String eventType;
    private boolean active;

    public RuleCreateDTO(String name, String description, String userId, String url, String eventType, boolean active) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.url = url;
        this.eventType = eventType;
        this.active = active;
    }

    public RuleCreateDTO(String name, String description, String userId, String url, String eventType) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.url = url;
        this.eventType = eventType;
    }

    public RuleCreateDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
