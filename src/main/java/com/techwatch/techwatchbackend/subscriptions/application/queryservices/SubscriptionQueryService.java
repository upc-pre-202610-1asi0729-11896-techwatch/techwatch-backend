package com.techwatch.techwatchbackend.subscriptions.application.queryservices;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Payment;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetActiveSubscriptionByUserIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetPaymentsBySubscriptionIdQuery;
import com.techwatch.techwatchbackend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryService {
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    Optional<Subscription> handle(GetActiveSubscriptionByUserIdQuery query);
    List<Payment> handle(GetPaymentsBySubscriptionIdQuery query);
}
