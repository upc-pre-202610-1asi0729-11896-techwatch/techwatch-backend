package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDate;

/**
 * Domain event published when a subscription is canceled.
 *
 * @param subscriptionId The canceled subscription id.
 * @param canceledAt The cancellation date.
 */
public record SubscriptionCanceledEvent(
        Long subscriptionId,
        LocalDate canceledAt
) {
}