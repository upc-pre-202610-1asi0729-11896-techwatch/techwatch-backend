package com.techwatch.techwatchbackend.subscriptions.domain.model.events;

import java.time.LocalDateTime;

public record PaymentProcessedEvent(
        Long paymentId,
        Long subscriptionId,
        Double amount,
        String currency,
        LocalDateTime processedAt) {
}
