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

    /**
     * Check whether the plan of the given user includes the given feature.
     * Users without an active subscription are evaluated against the free plan.
     *
     * @param userId The user id
     * @param feature The feature to check
     * @return true if the user's plan includes the feature
     */
    boolean hasFeature(Long userId, PlanFeature feature);
}
