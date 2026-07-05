package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for a money value object.
 */
@Embeddable
public class MoneyPersistenceEmbeddable {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;

    public MoneyPersistenceEmbeddable() {
    }

    public MoneyPersistenceEmbeddable(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
