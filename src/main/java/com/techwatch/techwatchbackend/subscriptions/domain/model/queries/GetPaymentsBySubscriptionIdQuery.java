package com.techwatch.techwatchbackend.subscriptions.domain.model.queries;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;

public record GetPaymentsBySubscriptionIdQuery(SubscriptionId subscriptionId) {
    public GetPaymentsBySubscriptionIdQuery {
        if (subscriptionId == null) {
            throw new IllegalArgumentException("subscriptionId cannot be null");
        }
    }
}
