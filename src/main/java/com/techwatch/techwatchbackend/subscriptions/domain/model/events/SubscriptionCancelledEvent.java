package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDateTime;

public record SubscriptionCancelledEvent(
        Long subscriptionId,
        LocalDateTime cancelledAt) {
}
