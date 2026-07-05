package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getPlanId().planId(),
                entity.getStatus().name(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getAutoRenew());
    }
}
