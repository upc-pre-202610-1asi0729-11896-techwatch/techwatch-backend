package com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects;

public record PlanId(Long planId) {
    public PlanId {
        if (planId == null || planId < 1) {
            throw new IllegalArgumentException("Plan id cannot be null or less than 1");
        }
    }
}
