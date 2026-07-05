package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.ChangeSubscriptionPlanResource;

/**
 * Assembler to convert a change subscription plan resource into a command.
 */
public class ChangeSubscriptionPlanCommandFromResourceAssembler {

    public static ChangeSubscriptionPlanCommand toCommandFromResource(
            Long subscriptionId,
            ChangeSubscriptionPlanResource resource
    ) {
        return new ChangeSubscriptionPlanCommand(
                subscriptionId,
                resource.newPlanName()
        );
    }
}