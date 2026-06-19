package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Resource used to request a subscription renewal.
 *
 * @param months number of months to extend the subscription
 */
public record RenewSubscriptionResource(
        @NotNull
        @Min(1)
        Integer months
) {
}