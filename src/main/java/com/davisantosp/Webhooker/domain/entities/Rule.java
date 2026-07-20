package com.davisantosp.Webhooker.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @NotNull
    @Column(nullable = false, length = 40)
    private String userId;

    @URL
    @NotBlank
    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private boolean active;

    @NotBlank
    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String sharedSecret;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public Rule(String name, String description, String userId, String url, String eventType) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.url = url;
        this.eventType = eventType;
        this.active = true;
    }

    public Rule(String name, String description, String userId, String url, boolean isActive, String eventType) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.url = url;
        this.active = isActive;
        this.eventType = eventType;
        this.sharedSecret = "placeholder-secret";
    }

    public Rule(String name, String description, String url, boolean isActive, String eventType) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.active = isActive;
        this.eventType = eventType;
        this.sharedSecret = "placeholder-secret";
    }

    public Rule(UUID id, String name, String description, String userId, String url, boolean isActive, String eventType, String sharedSecret, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.url = url;
        this.active = isActive;
        this.eventType = eventType;
        this.sharedSecret = sharedSecret;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Rule() {
    }

    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean status) {
        this.active = status;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
