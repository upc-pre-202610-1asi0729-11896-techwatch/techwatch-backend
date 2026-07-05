package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

public record SubscriptionCreatedEvent(
        Long subscriptionId,
        Long userId,
        Long planId,
        Boolean autoRenew) {
}
