package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionMetricPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for consumption metric persistence entities.
 */
@Repository
public interface ConsumptionMetricPersistenceRepository extends JpaRepository<ConsumptionMetricPersistenceEntity, Long> {
    List<ConsumptionMetricPersistenceEntity> findByPropertyId(PropertyId propertyId);
}
