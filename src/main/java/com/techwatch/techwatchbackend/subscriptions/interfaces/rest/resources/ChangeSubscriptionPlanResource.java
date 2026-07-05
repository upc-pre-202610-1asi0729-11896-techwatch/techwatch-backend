package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeSubscriptionPlanResource(
        @Schema(description = "New plan id", example = "2")
        Long newPlanId) {
    public ChangeSubscriptionPlanResource {
        if (newPlanId == null) {
            throw new IllegalArgumentException("newPlanId is required");
        }
    }
}
