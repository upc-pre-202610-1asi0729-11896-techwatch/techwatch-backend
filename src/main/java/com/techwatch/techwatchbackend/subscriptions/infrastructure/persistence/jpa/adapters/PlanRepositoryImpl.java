package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.assemblers.PlanPersistenceAssembler;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository planPersistenceRepository;

    public PlanRepositoryImpl(PlanPersistenceRepository planPersistenceRepository) {
        this.planPersistenceRepository = planPersistenceRepository;
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return planPersistenceRepository.findById(id)
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Plan> findByType(PlanType type) {
        return planPersistenceRepository.findByType(type)
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Plan> findAll() {
        return planPersistenceRepository.findAll().stream()
                .map(PlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Plan save(Plan plan) {
        var saved = planPersistenceRepository.save(PlanPersistenceAssembler.toPersistenceFromDomain(plan));
        return PlanPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public long count() {
        return planPersistenceRepository.count();
    }
}
