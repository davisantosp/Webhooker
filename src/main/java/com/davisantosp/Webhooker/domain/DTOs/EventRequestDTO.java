package com.davisantosp.Webhooker.domain.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record EventRequestDTO(
    @NotBlank String eventType,
    @NotNull String userId,
    @NotNull Map<String, Object> payload
){}
