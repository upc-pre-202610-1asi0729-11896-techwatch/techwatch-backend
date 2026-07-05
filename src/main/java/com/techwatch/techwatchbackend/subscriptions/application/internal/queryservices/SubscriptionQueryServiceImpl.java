package com.techwatch.techwatchbackend.subscriptions.application.internal.queryservices;

import com.techwatch.techwatchbackend.subscriptions.application.queryservices.SubscriptionQueryService;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetPaymentsBySubscriptionIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PaymentRepository;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository,
                                        PaymentRepository paymentRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.subscriptionId());
    }

    @Override
    public Optional<Subscription> handle(GetActiveSubscriptionByUserIdQuery query) {
        return subscriptionRepository.findActiveByUserId(query.userId());
    }

    @Override
    public List<Payment> handle(GetPaymentsBySubscriptionIdQuery query) {
        return paymentRepository.findAllBySubscriptionId(query.subscriptionId());
    }
}
