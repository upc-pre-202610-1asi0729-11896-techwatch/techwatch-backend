package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Resource used to request a subscription plan change.
 *
 * @param newPlanName the new subscription plan name
 */
@Schema(description = "Request payload used to change the plan of an existing subscription")
public record ChangeSubscriptionPlanResource(
        @Schema(
                description = "New subscription plan name",
                example = "PREMIUM"
        )
        @NotBlank
        String newPlanName
) {
}