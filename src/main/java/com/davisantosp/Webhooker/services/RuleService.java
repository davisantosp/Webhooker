package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.RuleCreateDTO;
import com.davisantosp.Webhooker.domain.DTOs.RuleResponseDTO;
import com.davisantosp.Webhooker.domain.entities.Rule;
import com.davisantosp.Webhooker.infra.exceptions.ResourceNotFoundException;
import com.davisantosp.Webhooker.repositories.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public List<RuleResponseDTO> findAll(){
        return ruleRepository.findAll()
                .stream()
                .map(x -> this.parseFromRule(x))
                .toList();
    }

    public RuleResponseDTO findById(UUID id){
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        RuleResponseDTO ruleResponseDTO = this.parseFromRule(rule);
        return ruleResponseDTO;
    }

    public RuleResponseDTO create(RuleCreateDTO ruleCreateDTO){
        Rule newRule = ruleRepository.save(
                this.parseFromRuleCreateDTO(ruleCreateDTO)
        );

        RuleResponseDTO ruleResponseDTO = this.parseFromRule(newRule);
        return ruleResponseDTO;
    }

    public RuleResponseDTO update(RuleCreateDTO updatedRule, UUID id){
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        rule.setName(updatedRule.getName());
        rule.setDescription(updatedRule.getDescription());
        rule.setActive(updatedRule.isActive());
        rule.setUrl(updatedRule.getUrl());
        rule.setEventType(updatedRule.getEventType());
        ruleRepository.save(rule);

        RuleResponseDTO ruleResponseDTO = this.parseFromRule(rule);
        return ruleResponseDTO;
    }

    public void delete(UUID id){
        if(!ruleRepository.existsById(id))
            throw new ResourceNotFoundException("Rule not found");
        ruleRepository.deleteById(id);
    }

    private Rule parseFromRuleCreateDTO(RuleCreateDTO ruleCreateDTO){
        Rule rule = new Rule(
                ruleCreateDTO.getName(),
                ruleCreateDTO.getDescription(),
                ruleCreateDTO.getUserId(),
                ruleCreateDTO.getUrl(),
                ruleCreateDTO.isActive(),
                ruleCreateDTO.getEventType()
        );
        return rule;
    }

    private RuleResponseDTO parseFromRule(Rule rule){
        RuleResponseDTO ruleResponseDTO = new RuleResponseDTO(
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getUserId(),
                rule.getUrl(),
                rule.getEventType(),
                rule.isActive(),
                rule.getCreatedAt(),
                rule.getUpdatedAt()
        );
        return ruleResponseDTO;
    }
}
