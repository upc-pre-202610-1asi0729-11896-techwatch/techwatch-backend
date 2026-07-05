package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanPersistenceRepository extends JpaRepository<PlanPersistenceEntity, Long> {
    Optional<PlanPersistenceEntity> findByType(PlanType type);
}
