package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;

public final class PaymentPersistenceAssembler {

    private PaymentPersistenceAssembler() {
    }

    public static Payment toDomainFromPersistence(PaymentPersistenceEntity entity) {
        if (entity == null) return null;

        var payment = new Payment();
        payment.setId(entity.getId());
        payment.setSubscriptionId(entity.getSubscriptionId());
        payment.setAmount(new Money(entity.getAmount().getAmount(), entity.getAmount().getCurrency()));
        payment.setStatus(entity.getStatus());
        payment.setExternalPaymentId(entity.getExternalPaymentId());
        payment.setProcessedAt(entity.getProcessedAt());
        return payment;
    }

    public static PaymentPersistenceEntity toPersistenceFromDomain(Payment payment) {
        if (payment == null) return null;

        var entity = new PaymentPersistenceEntity();
        if (payment.getId() != null) {
            entity.setId(payment.getId());
        }
        entity.setSubscriptionId(payment.getSubscriptionId());
        entity.setAmount(new MoneyPersistenceEmbeddable(payment.getAmount().amount(), payment.getAmount().currency()));
        entity.setStatus(payment.getStatus());
        entity.setExternalPaymentId(payment.getExternalPaymentId());
        entity.setProcessedAt(payment.getProcessedAt());
        return entity;
    }
}
