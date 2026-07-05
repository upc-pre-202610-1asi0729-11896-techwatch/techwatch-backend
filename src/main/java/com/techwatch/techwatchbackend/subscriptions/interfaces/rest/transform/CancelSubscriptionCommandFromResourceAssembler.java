package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CancelSubscriptionCommand;

/**
 * Assembler to convert a subscription id into a cancel subscription command.
 */
public class CancelSubscriptionCommandFromResourceAssembler {

    public static CancelSubscriptionCommand toCommandFromResource(Long subscriptionId) {
        return new CancelSubscriptionCommand(subscriptionId);
    }
}