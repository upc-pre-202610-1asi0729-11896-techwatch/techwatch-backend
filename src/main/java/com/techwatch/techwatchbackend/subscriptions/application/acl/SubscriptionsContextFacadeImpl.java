package com.techwatch.techwatchbackend.subscriptions.application.acl;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import com.techwatch.techwatchbackend.subscriptions.interfaces.acl.PlanFeature;
import com.techwatch.techwatchbackend.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionsContextFacadeImpl implements SubscriptionsContextFacade {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionsContextFacadeImpl(SubscriptionRepository subscriptionRepository,
                                          PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public int fetchMaxDevicesForUser(Long userId) {
        return fetchPlanForUser(userId).map(Plan::getMaxDevices).orElse(0);
    }

    @Override
    public boolean hasFeature(Long userId, PlanFeature feature) {
        return fetchPlanForUser(userId)
                .map(plan -> switch (feature) {
                    case ADVANCED_METRICS -> plan.getHasAdvancedMetrics();
                    case CUSTOM_REPORTS -> plan.getHasCustomReports();
                    case ALERTS -> plan.getHasAlerts();
                    case UNLIMITED_HISTORY -> plan.getHasUnlimitedHistory();
                })
                .orElse(false);
    }

    /**
     * Resolves the plan that applies to the given user: the plan of their active subscription, or the
     * free plan when they have none.
     */
    private Optional<Plan> fetchPlanForUser(Long userId) {
        return subscriptionRepository.findActiveByUserId(new UserId(userId))
                .flatMap(subscription -> planRepository.findById(subscription.getPlanId().planId()))
                .or(() -> planRepository.findByType(PlanType.FREE));
    }
}
