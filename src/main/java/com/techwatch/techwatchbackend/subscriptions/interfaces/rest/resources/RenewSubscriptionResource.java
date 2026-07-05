package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

public record RenewSubscriptionResource(
        @Schema(description = "Number of months to extend the subscription", example = "1")
        Integer months) {
    public RenewSubscriptionResource {
        if (months == null) {
            throw new IllegalArgumentException("months is required");
        }
    }
}
