package com.techwatch.techwatchbackend.subscriptions.domain.model.commands;

public record ProcessPaymentCommand(Long subscriptionId, Double amount, String currency) {
    public ProcessPaymentCommand {
        if (subscriptionId == null || subscriptionId < 1) {
            throw new IllegalArgumentException("subscriptionId cannot be null or less than 1");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("amount cannot be null or less than or equal to 0");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency cannot be null or blank");
        }
    }
}
