package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

/**
 * Command to change the plan of an existing subscription.
 *
 * @param subscriptionId The subscription id.
 * @param newPlanName The new subscription plan name.
 */
public record ChangeSubscriptionPlanCommand(
        Long subscriptionId,
        String newPlanName
) {
    public ChangeSubscriptionPlanCommand {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("subscriptionId cannot be null or less than 1");
        }

        if (newPlanName == null || newPlanName.isBlank()) {
            throw new IllegalArgumentException("newPlanName cannot be null or blank");
        }
    }
}