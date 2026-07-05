package com.techwatch.techwatchbackend.subscriptions.interfaces.rest.transform;

import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(resource.userId(), resource.planId(), resource.autoRenew());
    }
}
