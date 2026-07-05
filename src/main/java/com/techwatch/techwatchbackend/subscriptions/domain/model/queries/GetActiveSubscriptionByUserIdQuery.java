package com.techwatch.techwatchbackend.subscriptions.domain.model.queries;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;

public record GetActiveSubscriptionByUserIdQuery(UserId userId) {
    public GetActiveSubscriptionByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
