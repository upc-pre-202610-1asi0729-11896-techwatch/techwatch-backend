package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ProcessPaymentCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.PaymentProcessedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PaymentStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Payment extends AbstractDomainAggregateRoot<Payment> {

    @Setter
    private Long id;

    @Setter
    private SubscriptionId subscriptionId;

    @Setter
    private Money amount;

    @Setter
    private PaymentStatus status;

    @Setter
    private String externalPaymentId;

    @Setter
    private LocalDateTime processedAt;

    public Payment() {
    }

    public Payment(ProcessPaymentCommand command) {
        this.subscriptionId = new SubscriptionId(command.subscriptionId());
        this.amount = new Money(command.amount(), command.currency());
        this.status = PaymentStatus.PENDING;
    }

    public Payment confirm(String externalPaymentId) {
        this.externalPaymentId = externalPaymentId;
        this.status = PaymentStatus.COMPLETED;
        this.processedAt = LocalDateTime.now();
        registerDomainEvent(new PaymentProcessedEvent(this.id, this.subscriptionId.subscriptionId(),
                this.amount.amount(), this.amount.currency(), this.processedAt));
        return this;
    }

    public Payment reject() {
        this.status = PaymentStatus.FAILED;
        this.processedAt = LocalDateTime.now();
        return this;
    }
}
