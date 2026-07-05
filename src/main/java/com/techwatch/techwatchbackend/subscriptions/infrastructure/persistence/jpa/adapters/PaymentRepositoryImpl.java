package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PaymentRepository;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.assemblers.PaymentPersistenceAssembler;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.repositories.PaymentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentPersistenceRepository paymentPersistenceRepository;

    public PaymentRepositoryImpl(PaymentPersistenceRepository paymentPersistenceRepository) {
        this.paymentPersistenceRepository = paymentPersistenceRepository;
    }

    @Override
    public List<Payment> findAllBySubscriptionId(SubscriptionId subscriptionId) {
        return paymentPersistenceRepository.findAllBySubscriptionId(subscriptionId).stream()
                .map(PaymentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Payment save(Payment payment) {
        var saved = paymentPersistenceRepository.save(PaymentPersistenceAssembler.toPersistenceFromDomain(payment));
        return PaymentPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
