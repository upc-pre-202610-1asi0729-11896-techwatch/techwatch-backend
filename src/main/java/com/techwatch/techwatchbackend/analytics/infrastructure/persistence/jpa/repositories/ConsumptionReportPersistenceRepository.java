package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for consumption report persistence entities.
 */
@Repository
public interface ConsumptionReportPersistenceRepository extends JpaRepository<ConsumptionReportPersistenceEntity, Long> {
    List<ConsumptionReportPersistenceEntity> findByPropertyId(PropertyId propertyId);
}
