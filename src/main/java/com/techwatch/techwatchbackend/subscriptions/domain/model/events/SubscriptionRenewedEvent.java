package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDate;

/**
 * Domain event published when a subscription is renewed.
 *
 * @param subscriptionId The renewed subscription id.
 * @param newEndDate The new subscription end date.
 */
public record SubscriptionRenewedEvent(
        Long subscriptionId,
        LocalDate newEndDate
) {
}