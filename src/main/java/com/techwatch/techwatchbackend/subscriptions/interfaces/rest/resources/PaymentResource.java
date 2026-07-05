package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources;

import java.time.LocalDateTime;

public record PaymentResource(
        Long id,
        Long subscriptionId,
        Double amount,
        String currency,
        String status,
        String externalPaymentId,
        LocalDateTime processedAt) {
}
