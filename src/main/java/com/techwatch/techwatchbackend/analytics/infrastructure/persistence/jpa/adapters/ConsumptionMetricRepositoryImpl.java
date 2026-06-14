package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionMetricRepository;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers.ConsumptionMetricPersistenceAssembler;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories.ConsumptionMetricPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the consumption metric domain repository port with Spring Data JPA.
 */
@Repository
public class ConsumptionMetricRepositoryImpl implements ConsumptionMetricRepository {

    private final ConsumptionMetricPersistenceRepository consumptionMetricPersistenceRepository;

    public ConsumptionMetricRepositoryImpl(ConsumptionMetricPersistenceRepository consumptionMetricPersistenceRepository) {
        this.consumptionMetricPersistenceRepository = consumptionMetricPersistenceRepository;
    }

    @Override
    public Optional<ConsumptionMetric> findById(Long id) {
        return consumptionMetricPersistenceRepository.findById(id).map(ConsumptionMetricPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ConsumptionMetric> findByPropertyId(PropertyId propertyId) {
        return consumptionMetricPersistenceRepository.findByPropertyId(propertyId).stream()
                .map(ConsumptionMetricPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ConsumptionMetric save(ConsumptionMetric metric) {
        var saved = consumptionMetricPersistenceRepository.save(ConsumptionMetricPersistenceAssembler.toPersistenceFromDomain(metric));
        return ConsumptionMetricPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
