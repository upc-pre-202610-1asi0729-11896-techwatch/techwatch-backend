package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Resource used to request a subscription renewal.
 *
 * @param months number of months to extend the subscription
 */
@Schema(description = "Request payload used to renew an existing subscription")
public record RenewSubscriptionResource(
        @Schema(
                description = "Number of months to extend the subscription",
                example = "1",
                minimum = "1"
        )
        @NotNull
        @Min(1)
        Integer months
) {
}