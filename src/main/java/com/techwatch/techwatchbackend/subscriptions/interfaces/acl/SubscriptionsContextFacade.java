package com.techwatch.techwatchbackend.subscriptions.interfaces.acl;

/**
 * Facade exposed by the Subscriptions context to other bounded contexts (published language).
 */
public interface SubscriptionsContextFacade {
    /**
     * Fetch the maximum number of devices allowed by the plan of the given user.
     * Users without an active subscription get the free plan limits.
     *
     * @param userId The user id
     * @return The maximum number of devices allowed
     */
    int fetchMaxDevicesForUser(Long userId);
}
