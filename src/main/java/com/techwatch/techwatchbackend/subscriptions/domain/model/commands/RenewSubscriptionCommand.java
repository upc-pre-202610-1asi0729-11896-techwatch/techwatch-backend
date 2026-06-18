package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

/**
 * Command to renew an existing subscription.
 *
 * @param subscriptionId The subscription id.
 * @param months The number of months to extend the subscription.
 */
public record RenewSubscriptionCommand(
        Long subscriptionId,
        Integer months
) {
    public RenewSubscriptionCommand {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("subscriptionId cannot be null or less than 1");
        }

        if (months == null || months < 1) {
            throw new IllegalArgumentException("months cannot be null or less than 1");
        }
    }
}