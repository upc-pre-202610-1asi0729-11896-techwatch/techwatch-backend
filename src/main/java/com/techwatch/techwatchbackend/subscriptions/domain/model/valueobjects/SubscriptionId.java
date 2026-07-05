package com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects;

public record SubscriptionId(Long subscriptionId) {
    public SubscriptionId {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("Subscription id cannot be null or less than 1");
        }
    }
}
