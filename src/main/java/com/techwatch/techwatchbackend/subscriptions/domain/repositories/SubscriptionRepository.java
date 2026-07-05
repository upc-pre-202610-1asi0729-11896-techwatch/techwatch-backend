package com.techwatch.techwatchbackend.subscriptions.domain.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;

import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);
    Optional<Subscription> findActiveByUserId(UserId userId);
    Subscription save(Subscription subscription);
}
