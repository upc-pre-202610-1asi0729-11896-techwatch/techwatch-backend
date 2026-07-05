package com.techwatch.techwatchbackend.subscriptions.domain.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    Optional<Plan> findById(Long id);
    Optional<Plan> findByType(PlanType type);
    List<Plan> findAll();
    Plan save(Plan plan);
    long count();
}
