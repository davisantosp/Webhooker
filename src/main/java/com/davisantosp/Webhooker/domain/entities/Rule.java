package com.davisantosp.Webhooker.domain.entities;

import com.davisantosp.Webhooker.domain.enums.RuleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @URL
    @NotBlank
    @Column(nullable = false, length = 2048)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RuleStatus status;

    @Column(nullable = false)
    private String sharedSecret;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    public Rule(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = RuleStatus.ACTIVE;
        //Proper method will be added later
        this.sharedSecret = "placeholder-secret";
    }

    public Rule(String name, String description, String url, RuleStatus status) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = status;
        //Proper method will be added later
        this.sharedSecret = "placeholder-secret";
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

    public RuleStatus getStatus() {
        return status;
    }

    public void setStatus(RuleStatus status) {
        this.status = status;
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
