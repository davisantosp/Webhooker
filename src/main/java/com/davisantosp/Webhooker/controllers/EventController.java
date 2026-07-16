package com.davisantosp.Webhooker.controllers;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.services.EventProducerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    EventProducerService eventProducerService;

    public EventController(@NotNull final EventProducerService eventProducerService) {
        this.eventProducerService = eventProducerService;
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody EventRequestDTO eventRequestDTO){
        eventProducerService.addEvent(eventRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
