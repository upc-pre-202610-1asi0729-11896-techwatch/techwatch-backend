package com.techwatch.techwatchbackend.subscriptions.domain.repositories;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * Subscription repository port.
 */
public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);
    List<Subscription> findAll();
    List<Subscription> findAllByUserId(Long userId);
    Subscription save(Subscription subscription);
    boolean existsById(Long id);
}