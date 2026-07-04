package com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects;

public record Money(Double amount, String currency) {
    public Money {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Money amount cannot be null or negative");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Money currency cannot be null or blank");
        }
    }
}
