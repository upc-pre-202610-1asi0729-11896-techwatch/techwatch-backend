package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getSubscriptionId().subscriptionId(),
                entity.getAmount().amount(),
                entity.getAmount().currency(),
                entity.getStatus().name(),
                entity.getExternalPaymentId(),
                entity.getProcessedAt());
    }
}
