package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDateTime;

public record SubscriptionPlanChangedEvent(
        Long subscriptionId,
        Long newPlanId,
        LocalDateTime changedAt) {
}
