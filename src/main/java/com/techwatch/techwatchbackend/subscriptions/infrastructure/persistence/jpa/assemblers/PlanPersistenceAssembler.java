package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;

public final class PlanPersistenceAssembler {

    private PlanPersistenceAssembler() {
    }

    public static Plan toDomainFromPersistence(PlanPersistenceEntity entity) {
        if (entity == null) return null;

        var plan = new Plan();
        plan.setId(entity.getId());
        plan.setName(entity.getName());
        plan.setType(entity.getType());
        plan.setPrice(new Money(entity.getPrice().getAmount(), entity.getPrice().getCurrency()));
        plan.setBillingCycle(entity.getBillingCycle());
        plan.setMaxDevices(entity.getMaxDevices());
        plan.setHasAdvancedMetrics(entity.getHasAdvancedMetrics());
        plan.setHasCustomReports(entity.getHasCustomReports());
        plan.setHasAlerts(entity.getHasAlerts());
        plan.setHasUnlimitedHistory(entity.getHasUnlimitedHistory());
        plan.setIsActive(entity.getIsActive());
        return plan;
    }

    public static PlanPersistenceEntity toPersistenceFromDomain(Plan plan) {
        if (plan == null) return null;

        var entity = new PlanPersistenceEntity();
        if (plan.getId() != null) {
            entity.setId(plan.getId());
        }
        entity.setName(plan.getName());
        entity.setType(plan.getType());
        entity.setPrice(new MoneyPersistenceEmbeddable(plan.getPrice().amount(), plan.getPrice().currency()));
        entity.setBillingCycle(plan.getBillingCycle());
        entity.setMaxDevices(plan.getMaxDevices());
        entity.setHasAdvancedMetrics(plan.getHasAdvancedMetrics());
        entity.setHasCustomReports(plan.getHasCustomReports());
        entity.setHasAlerts(plan.getHasAlerts());
        entity.setHasUnlimitedHistory(plan.getHasUnlimitedHistory());
        entity.setIsActive(plan.getIsActive());
        return entity;
    }
}
