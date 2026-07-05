package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.ChangeSubscriptionPlanResource;

public class ChangeSubscriptionPlanCommandFromResourceAssembler {
    public static ChangeSubscriptionPlanCommand toCommandFromResource(Long subscriptionId,
                                                                      ChangeSubscriptionPlanResource resource) {
        return new ChangeSubscriptionPlanCommand(subscriptionId, resource.newPlanId());
    }
}
