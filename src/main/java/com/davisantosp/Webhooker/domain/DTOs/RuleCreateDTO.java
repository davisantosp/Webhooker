package com.davisantosp.Webhooker.domain.DTOs;

import com.davisantosp.Webhooker.domain.enums.RuleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

public class RuleCreateDTO {

    @NotBlank
    private String name;
    private String description;
    @URL
    @NotBlank
    @Size(max = 2048)
    private String url;
    private RuleStatus status;

    public RuleCreateDTO(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public RuleCreateDTO(String name, String description, String url, RuleStatus status) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = status;
    }

    public RuleCreateDTO() {
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

    public RuleStatus getStatus() {
        return status;
    }

    public void setStatus(RuleStatus status) {
        this.status = status;
    }
}
