package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.RenewSubscriptionResource;

/**
 * Assembler to convert a renew subscription resource into a command.
 */
public class RenewSubscriptionCommandFromResourceAssembler {

    public static RenewSubscriptionCommand toCommandFromResource(
            Long subscriptionId,
            RenewSubscriptionResource resource
    ) {
        return new RenewSubscriptionCommand(
                subscriptionId,
                resource.months()
        );
    }
}