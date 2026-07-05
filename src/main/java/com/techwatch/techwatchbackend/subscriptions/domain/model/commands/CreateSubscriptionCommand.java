package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

public record CreateSubscriptionCommand(Long userId, Long planId, Boolean autoRenew) {
    public CreateSubscriptionCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (planId == null || planId < 1) {
            throw new IllegalArgumentException("planId cannot be null or less than 1");
        }
        if (autoRenew == null) {
            throw new IllegalArgumentException("autoRenew cannot be null");
        }
    }
}
