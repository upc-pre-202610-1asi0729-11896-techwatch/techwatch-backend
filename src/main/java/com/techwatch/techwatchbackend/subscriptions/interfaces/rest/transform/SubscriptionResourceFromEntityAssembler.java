package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.SubscriptionResource;

/**
 * Assembler to convert a subscription aggregate into a REST resource.
 */
public class SubscriptionResourceFromEntityAssembler {

    public static SubscriptionResource toResourceFromEntity(Subscription subscription) {
        return new SubscriptionResource(
                subscription.getId(),
                subscription.getUserId(),
                subscription.getPlanName(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStatus()
        );
    }
}