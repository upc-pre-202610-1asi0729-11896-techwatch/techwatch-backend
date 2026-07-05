package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDate;

/**
 * Domain event published when a subscription plan is changed.
 *
 * @param subscriptionId The subscription id.
 * @param newPlanName The new subscription plan name.
 * @param changedAt The date when the plan was changed.
 */
public record SubscriptionPlanChangedEvent(
        Long subscriptionId,
        String newPlanName,
        LocalDate changedAt
) {
}