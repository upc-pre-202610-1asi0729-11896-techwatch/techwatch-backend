package com.techwatch.techwatchbackend.devices.application.internal.outboundservices.acl;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL service towards the Subscriptions context.
 */
@Service
public class ExternalSubscriptionService {

    private final SubscriptionsContextFacade subscriptionsContextFacade;

    public ExternalSubscriptionService(SubscriptionsContextFacade subscriptionsContextFacade) {
        this.subscriptionsContextFacade = subscriptionsContextFacade;
    }

    /**
     * Fetch the maximum number of devices allowed by the plan of the given user.
     *
     * @param userId The user id
     * @return The maximum number of devices allowed
     */
    public int fetchMaxDevicesForUser(UserId userId) {
        return subscriptionsContextFacade.fetchMaxDevicesForUser(userId.userId());
    }
}
