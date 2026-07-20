package com.davisantosp.Webhooker.services;

import com.davisantosp.Webhooker.domain.DTOs.RuleCreateDTO;
import com.davisantosp.Webhooker.domain.DTOs.RuleResponseDTO;
import com.davisantosp.Webhooker.domain.entities.Rule;

import com.davisantosp.Webhooker.infra.exceptions.ResourceNotFoundException;
import com.davisantosp.Webhooker.repositories.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RuleService")
class RuleServiceTest {

    @Mock
    RuleRepository ruleRepository;

    @InjectMocks
    RuleService ruleService;

    RuleCreateDTO ruleCreateDTO;
    Rule rule;
    String userId;

    @BeforeEach
    void setup(){
        userId = String.valueOf(UUID.randomUUID());
        ruleCreateDTO = new RuleCreateDTO(
                "Default Name",
                "Default description",
                userId,
                "https://defaulturl.com",
                "user.created",
                true
        );
        rule = new Rule(
                UUID.randomUUID(),
                "Default Name",
                "Default description",
                userId,
                "https://defaulturl.com",
                true,
                "user.created",
                "Default-shared-secret",
                Instant.now(),
                Instant.now()
        );
    }

    @Nested
    @DisplayName("findAll()")
    class FindAllTest {
        @Test
        @DisplayName("It should return a list with a ruleResponseDTO similar to rule")
        void itShouldReturnAListWithARuleResponseDTOFromRule() {
            when(ruleRepository.findAll())
                    .thenReturn(Arrays.asList(rule));

            List<RuleResponseDTO> ruleResponseDTOList = ruleService.findAll();

            RuleResponseDTO ruleResponseDTO = ruleResponseDTOList.get(0);
            assertEquals(ruleResponseDTO.getName(), rule.getName());
            assertEquals(ruleResponseDTO.getDescription(), rule.getDescription());
            assertEquals(ruleResponseDTO.getUrl(), rule.getUrl());
            assertEquals(ruleResponseDTO.getEventType(), rule.getEventType());
            assertEquals(ruleResponseDTO.isActive(), rule.isActive());
            assertEquals(ruleResponseDTO.getCreatedAt(), rule.getCreatedAt());
            assertEquals(ruleResponseDTO.getUpdatedAt(), rule.getUpdatedAt());
            assertEquals(ruleResponseDTO.getUserId(), rule.getUserId());

            verify(ruleRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("It should return an empty list")
        void itShouldReturnAnEmptyList() {
            when(ruleRepository.findAll())
                    .thenReturn(Collections.emptyList());

            List<RuleResponseDTO> ruleResponseDTOList = ruleService.findAll();

            assertNotNull(ruleResponseDTOList);
            assertEquals(0, ruleResponseDTOList.toArray().length);

            verify(ruleRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("findById()")
    class FindByIdTest{

        @Test
        @DisplayName("It should return a ruleResponseDTO similar to rule")
        void itShouldReturnARuleResponseDTOSimilarToRule() {
            var id = rule.getId();
            when(ruleRepository.findById(id))
                    .thenReturn(Optional.of(rule));

            RuleResponseDTO testRule = ruleService.findById(id);

            assertEquals(testRule.getId(), rule.getId());
            assertEquals(testRule.getName(), rule.getName());
            assertEquals(testRule.getDescription(), rule.getDescription());
            assertEquals(testRule.getUrl(), rule.getUrl());
            assertEquals(testRule.getEventType(), rule.getEventType());
            assertEquals(testRule.getCreatedAt(), rule.getCreatedAt());
            assertEquals(testRule.getUpdatedAt(), rule.getUpdatedAt());
            assertEquals(testRule.getUserId(), rule.getUserId());

            verify(ruleRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("it should return a ResourceNotFoundException")
        void itShouldReturnAResourceNotFoundException() {
            var id = rule.getId();
            when(ruleRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.empty());

            ResourceNotFoundException e = assertThrows(
                    ResourceNotFoundException.class,
                    () -> ruleService.findById(id)
            );

            assertEquals("Rule not found", e.getMessage());

            verify(ruleRepository, times(1)).findById(any(UUID.class));
        }
    }

    @Nested
    @DisplayName("create()")
    class CreateTest {

        @Test
        @DisplayName("it should return a RuleResponseDTO successfully")
        void itShouldReturnARuleResponseDTOSuccessfully() {
            ArgumentCaptor<Rule> ruleCaptor = ArgumentCaptor.forClass(Rule.class);

            when(ruleRepository.save(any(Rule.class)))
                    .thenReturn(rule);

            RuleResponseDTO ruleResponseDTO = ruleService.create(ruleCreateDTO);

            verify(ruleRepository, times(1)).save(ruleCaptor.capture());

            Rule ruleSentToDb = ruleCaptor.getValue();

            assertEquals(ruleCreateDTO.getName(), ruleSentToDb.getName());
            assertEquals(ruleCreateDTO.getUrl(), ruleSentToDb.getUrl());
            assertEquals(ruleCreateDTO.getEventType(), ruleSentToDb.getEventType());
            assertEquals(ruleCreateDTO.isActive(), ruleSentToDb.isActive());
            assertEquals(ruleCreateDTO.getUserId(), ruleSentToDb.getUserId());
            assertNotNull(ruleSentToDb.getSharedSecret());

            assertEquals(ruleResponseDTO.getName(), ruleCreateDTO.getName());
            assertEquals(ruleResponseDTO.getDescription(), ruleCreateDTO.getDescription());
            assertEquals(ruleResponseDTO.getUrl(), ruleCreateDTO.getUrl());
            assertEquals(ruleResponseDTO.getEventType(), ruleCreateDTO.getEventType());
            assertEquals(ruleResponseDTO.isActive(), ruleCreateDTO.isActive());
            assertEquals(ruleResponseDTO.getUserId(), ruleCreateDTO.getUserId());
        }
    }

    @Nested
    @DisplayName("update()")
    class UpdateTest {

        RuleCreateDTO updatedRule;

        @BeforeEach
        void setUpdatedRule(){
            updatedRule = new RuleCreateDTO(
                    "New name",
                    "New description",
                    userId,
                    "http://newurl.com",
                    "order.created",
                    false
            );
        }

        @Test
        @DisplayName("It should return the same rule with different info")
        void itShouldReturnTheSameRuleWithDifferentInfo(){
            var id = rule.getId();
            when(ruleRepository.findById(id))
                    .thenReturn(Optional.of(rule));

            RuleResponseDTO ruleResponseDTO = ruleService.update(updatedRule, id);

            assertEquals(ruleResponseDTO.getName(), updatedRule.getName());
            assertEquals(ruleResponseDTO.getDescription(), updatedRule.getDescription());
            assertEquals(ruleResponseDTO.getUrl(), updatedRule.getUrl());
            assertEquals(ruleResponseDTO.getEventType(), updatedRule.getEventType());
            assertEquals(ruleResponseDTO.isActive(), updatedRule.isActive());

            assertEquals(ruleResponseDTO.getId(), rule.getId());
            assertEquals(ruleResponseDTO.getCreatedAt(), rule.getCreatedAt());
            assertEquals(ruleResponseDTO.getUpdatedAt(), rule.getUpdatedAt());
            assertEquals(ruleResponseDTO.getUserId(), rule.getUserId());

            verify(ruleRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("It should return a ResourceNotFoundException and not call save()")
        void itShouldReturnAResourceNotFoundException(){
            var id = rule.getId();
            when(ruleRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.empty());

            ResourceNotFoundException e = assertThrows(
                    ResourceNotFoundException.class,
                    () -> ruleService.update(updatedRule, id)
            );

            assertEquals("Rule not found", e.getMessage());

            verify(ruleRepository, times(1)).findById(any(UUID.class));
            verify(ruleRepository, times(0)).save(any(Rule.class));
        }
    }

    @Nested
    @DisplayName("delete()")
    class DeleteTest{

        @Test
        @DisplayName("it should delete successfully")
        void itShouldDeleteSuccessfully(){
            var id = rule.getId();
            when(ruleRepository.existsById(id))
                    .thenReturn(true);
            doNothing().when(ruleRepository).deleteById(id);

            ruleService.delete(id);

            verify(ruleRepository, times(1)).existsById(id);
            verify(ruleRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("it should return a ResourceNotFoundException and not call delete()")
        void itShouldReturnAResourceNotFoundExceptionAndNotCallDelete(){
            var id = rule.getId();
            when(ruleRepository.existsById(any(UUID.class)))
                    .thenReturn(false);

            ResourceNotFoundException e = assertThrows(
                    ResourceNotFoundException.class,
                    () -> ruleService.delete(id)
            );

            assertEquals("Rule not found", e.getMessage());

            verify(ruleRepository, times(1)).existsById(any(UUID.class));
            verify(ruleRepository, times(0)).deleteById(any(UUID.class));
        }
    }
}
