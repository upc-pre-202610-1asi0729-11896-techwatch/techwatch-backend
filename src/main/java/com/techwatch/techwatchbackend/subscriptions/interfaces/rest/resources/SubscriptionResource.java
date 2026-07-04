package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import java.time.LocalDateTime;

public record SubscriptionResource(
        Long id,
        Long userId,
        Long planId,
        String status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean autoRenew) {
}
