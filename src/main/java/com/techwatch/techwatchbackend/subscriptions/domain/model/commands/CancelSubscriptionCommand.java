package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

/**
 * Command to cancel an existing subscription.
 *
 * @param subscriptionId The subscription id.
 */
public record CancelSubscriptionCommand(
        Long subscriptionId
) {
    public CancelSubscriptionCommand {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("subscriptionId cannot be null or less than 1");
        }
    }
}