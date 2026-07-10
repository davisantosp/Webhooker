package com.davisantosp.Webhooker.controllers;

import com.davisantosp.Webhooker.domain.DTOs.RuleCreateDTO;
import com.davisantosp.Webhooker.domain.DTOs.RuleResponseDTO;
import com.davisantosp.Webhooker.services.RuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public ResponseEntity<List<RuleResponseDTO>> index() {
        return ResponseEntity.ok(ruleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleResponseDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ruleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RuleResponseDTO> post(@Valid @RequestBody RuleCreateDTO requestBody) {
        RuleResponseDTO createdRule = ruleService.create(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleResponseDTO> put(@Valid @RequestBody RuleCreateDTO requestBody, @PathVariable UUID id) {
        return ResponseEntity.ok(ruleService.update(requestBody, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ruleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
