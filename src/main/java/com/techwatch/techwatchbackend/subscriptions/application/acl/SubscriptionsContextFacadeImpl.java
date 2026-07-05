package com.techwatch.techwatchbackend.subscriptions.application.acl;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import com.techwatch.techwatchbackend.subscriptions.interfaces.acl.SubscriptionsContextFacade;
import org.springframework.stereotype.Service;

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
        var plan = subscriptionRepository.findActiveByUserId(new UserId(userId))
                .flatMap(subscription -> planRepository.findById(subscription.getPlanId().planId()))
                .or(() -> planRepository.findByType(PlanType.FREE));
        return plan.map(value -> value.getMaxDevices()).orElse(0);
    }
}
