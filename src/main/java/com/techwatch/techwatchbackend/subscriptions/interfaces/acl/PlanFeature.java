package com.techwatch.techwatchbackend.subscriptions.interfaces.acl;

/**
 * Freemium features gated by a subscription plan, queried through {@link SubscriptionsContextFacade#hasFeature}.
 */
public enum PlanFeature {
    ADVANCED_METRICS,
    CUSTOM_REPORTS,
    ALERTS,
    UNLIMITED_HISTORY
}
