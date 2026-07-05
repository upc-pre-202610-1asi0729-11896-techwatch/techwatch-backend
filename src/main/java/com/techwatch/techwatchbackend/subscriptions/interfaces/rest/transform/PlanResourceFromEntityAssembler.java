package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.PlanResource;

public class PlanResourceFromEntityAssembler {
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
                entity.getId(),
                entity.getName(),
                entity.getType().name(),
                entity.getPrice().amount(),
                entity.getPrice().currency(),
                entity.getBillingCycle().name(),
                entity.getMaxDevices(),
                entity.getHasAdvancedMetrics(),
                entity.getHasCustomReports(),
                entity.getHasAlerts(),
                entity.getHasUnlimitedHistory(),
                entity.getIsActive());
    }
}
