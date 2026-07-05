package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;

public final class SubscriptionPersistenceAssembler {

    private SubscriptionPersistenceAssembler() {
    }

    public static Subscription toDomainFromPersistence(SubscriptionPersistenceEntity entity) {
        if (entity == null) return null;

        var subscription = new Subscription();
        subscription.setId(entity.getId());
        subscription.setUserId(entity.getUserId());
        subscription.setPlanId(entity.getPlanId());
        subscription.setStatus(entity.getStatus());
        subscription.setStartDate(entity.getStartDate());
        subscription.setEndDate(entity.getEndDate());
        subscription.setAutoRenew(entity.getAutoRenew());
        return subscription;
    }

    public static SubscriptionPersistenceEntity toPersistenceFromDomain(Subscription subscription) {
        if (subscription == null) return null;

        var entity = new SubscriptionPersistenceEntity();
        if (subscription.getId() != null) {
            entity.setId(subscription.getId());
        }
        entity.setUserId(subscription.getUserId());
        entity.setPlanId(subscription.getPlanId());
        entity.setStatus(subscription.getStatus());
        entity.setStartDate(subscription.getStartDate());
        entity.setEndDate(subscription.getEndDate());
        entity.setAutoRenew(subscription.getAutoRenew());
        return entity;
    }
}
