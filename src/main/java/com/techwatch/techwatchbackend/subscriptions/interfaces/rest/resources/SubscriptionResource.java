package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Resource returned by the subscriptions API.
 *
 * @param id subscription id
 * @param userId owner user id
 * @param planName subscription plan name
 * @param startDate subscription start date
 * @param endDate subscription end date
 * @param status subscription status
 */
@Schema(description = "Subscription response resource")
public record SubscriptionResource(
        @Schema(description = "Subscription id", example = "1")
        Long id,

        @Schema(description = "Owner user id", example = "5")
        Long userId,

        @Schema(description = "Subscription plan name", example = "PREMIUM")
        String planName,

        @Schema(description = "Subscription start date", example = "2026-06-01")
        LocalDate startDate,

        @Schema(description = "Subscription end date", example = "2026-07-01")
        LocalDate endDate,

        @Schema(
                description = "Subscription status",
                example = "ACTIVE",
                allowableValues = {"ACTIVE", "EXPIRED", "CANCELED"}
        )
        SubscriptionStatus status
) {
}