package com.techwatch.techwatchbackend.subscriptions.domain.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findAllBySubscriptionId(SubscriptionId subscriptionId);
    Payment save(Payment payment);
}
