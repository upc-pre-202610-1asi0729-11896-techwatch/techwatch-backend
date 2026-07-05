package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDateTime;

public record SubscriptionRenewedEvent(
        Long subscriptionId,
        LocalDateTime newEndDate) {
}
