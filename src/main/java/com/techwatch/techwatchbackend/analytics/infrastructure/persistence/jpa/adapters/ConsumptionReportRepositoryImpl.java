package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionReportRepository;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers.ConsumptionReportPersistenceAssembler;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories.ConsumptionReportPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the consumption report domain repository port with Spring Data JPA.
 */
@Repository
public class ConsumptionReportRepositoryImpl implements ConsumptionReportRepository {

    private final ConsumptionReportPersistenceRepository consumptionReportPersistenceRepository;

    public ConsumptionReportRepositoryImpl(ConsumptionReportPersistenceRepository consumptionReportPersistenceRepository) {
        this.consumptionReportPersistenceRepository = consumptionReportPersistenceRepository;
    }

    @Override
    public Optional<ConsumptionReport> findById(Long id) {
        return consumptionReportPersistenceRepository.findById(id).map(ConsumptionReportPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ConsumptionReport> findByPropertyId(PropertyId propertyId) {
        return consumptionReportPersistenceRepository.findByPropertyId(propertyId).stream()
                .map(ConsumptionReportPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ConsumptionReport save(ConsumptionReport report) {
        var saved = consumptionReportPersistenceRepository.save(ConsumptionReportPersistenceAssembler.toPersistenceFromDomain(report));
        return ConsumptionReportPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
