package com.davisantosp.Webhooker.repositories;

import com.davisantosp.Webhooker.domain.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {
}
