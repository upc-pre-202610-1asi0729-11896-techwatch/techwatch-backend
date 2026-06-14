package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.analytics.domain.repositories.ConsumptionAlertRepository;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers.ConsumptionAlertPersistenceAssembler;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.repositories.ConsumptionAlertPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the consumption alert domain repository port with Spring Data JPA.
 */
@Repository
public class ConsumptionAlertRepositoryImpl implements ConsumptionAlertRepository {

    private final ConsumptionAlertPersistenceRepository consumptionAlertPersistenceRepository;

    public ConsumptionAlertRepositoryImpl(ConsumptionAlertPersistenceRepository consumptionAlertPersistenceRepository) {
        this.consumptionAlertPersistenceRepository = consumptionAlertPersistenceRepository;
    }

    @Override
    public Optional<ConsumptionAlert> findById(Long id) {
        return consumptionAlertPersistenceRepository.findById(id).map(ConsumptionAlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ConsumptionAlert> findByUserId(UserId userId) {
        return consumptionAlertPersistenceRepository.findByUserId(userId).stream()
                .map(ConsumptionAlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ConsumptionAlert save(ConsumptionAlert alert) {
        var saved = consumptionAlertPersistenceRepository.save(ConsumptionAlertPersistenceAssembler.toPersistenceFromDomain(alert));
        return ConsumptionAlertPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
