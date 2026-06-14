package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionAlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for consumption alert persistence entities.
 */
@Repository
public interface ConsumptionAlertPersistenceRepository extends JpaRepository<ConsumptionAlertPersistenceEntity, Long> {
    List<ConsumptionAlertPersistenceEntity> findByUserId(UserId userId);
}
