package com.davisantosp.Webhooker.controllers;

import com.davisantosp.Webhooker.domain.DTOs.EventRequestDTO;
import com.davisantosp.Webhooker.services.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    EventService eventService;

    public EventController(@NotNull final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody EventRequestDTO eventRequestDTO){
        eventService.addEvent(eventRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
