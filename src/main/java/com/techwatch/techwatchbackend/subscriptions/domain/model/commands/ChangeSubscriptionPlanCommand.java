package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

public record ChangeSubscriptionPlanCommand(Long subscriptionId, Long newPlanId) {
    public ChangeSubscriptionPlanCommand {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("subscriptionId cannot be null or less than 1");
        }
        if (newPlanId == null || newPlanId < 1) {
            throw new IllegalArgumentException("newPlanId cannot be null or less than 1");
        }
    }
}
