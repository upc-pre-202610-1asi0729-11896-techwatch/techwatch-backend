package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

public record CreateSubscriptionResource(Long userId, Long planId, Boolean autoRenew) {
    public CreateSubscriptionResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (planId == null) {
            throw new IllegalArgumentException("planId is required");
        }
        if (autoRenew == null) {
            throw new IllegalArgumentException("autoRenew is required");
        }
    }
}
