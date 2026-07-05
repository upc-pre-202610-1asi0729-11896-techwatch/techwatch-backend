package com.techwatch.techwatchbackend.subscriptions.application.acl;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Subscription;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanId;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.SubscriptionRepository;
import com.techwatch.techwatchbackend.subscriptions.interfaces.acl.PlanFeature;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionsContextFacadeImplTest {

    private Plan buildPlan(Long id, PlanType type, boolean allFeatures, int maxDevices) {
        var plan = new Plan();
        plan.setId(id);
        plan.setType(type);
        plan.setPrice(new Money(0.0, "USD"));
        plan.setMaxDevices(maxDevices);
        plan.setHasAdvancedMetrics(allFeatures);
        plan.setHasCustomReports(allFeatures);
        plan.setHasAlerts(true);
        plan.setHasUnlimitedHistory(allFeatures);
        plan.setIsActive(true);
        return plan;
    }

    @Test
    void hasFeatureReturnsTrueWhenActiveSubscriptionPlanIncludesFeature() {
        var freePlan = buildPlan(1L, PlanType.FREE, false, 5);
        var premiumPlan = buildPlan(2L, PlanType.PREMIUM, true, 50);
        var subscription = new Subscription();
        subscription.setUserId(new UserId(10L));
        subscription.setPlanId(new PlanId(2L));
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        var facade = new SubscriptionsContextFacadeImpl(
                new FakeSubscriptionRepository(subscription),
                new FakePlanRepository(List.of(freePlan, premiumPlan)));

        assertTrue(facade.hasFeature(10L, PlanFeature.ADVANCED_METRICS));
        assertTrue(facade.hasFeature(10L, PlanFeature.CUSTOM_REPORTS));
        assertTrue(facade.hasFeature(10L, PlanFeature.UNLIMITED_HISTORY));
        assertEquals(50, facade.fetchMaxDevicesForUser(10L));
    }

    @Test
    void hasFeatureFallsBackToFreePlanWhenUserHasNoActiveSubscription() {
        var freePlan = buildPlan(1L, PlanType.FREE, false, 5);

        var facade = new SubscriptionsContextFacadeImpl(
                new FakeSubscriptionRepository(null),
                new FakePlanRepository(List.of(freePlan)));

        assertFalse(facade.hasFeature(99L, PlanFeature.ADVANCED_METRICS));
        assertTrue(facade.hasFeature(99L, PlanFeature.ALERTS));
        assertEquals(5, facade.fetchMaxDevicesForUser(99L));
    }

    private record FakeSubscriptionRepository(Subscription activeSubscription) implements SubscriptionRepository {
        @Override
        public Optional<Subscription> findById(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Subscription> findActiveByUserId(UserId userId) {
            return Optional.ofNullable(activeSubscription);
        }

        @Override
        public Subscription save(Subscription subscription) {
            throw new UnsupportedOperationException();
        }
    }

    private record FakePlanRepository(List<Plan> plans) implements PlanRepository {
        @Override
        public Optional<Plan> findById(Long id) {
            return plans.stream().filter(plan -> plan.getId().equals(id)).findFirst();
        }

        @Override
        public Optional<Plan> findByType(PlanType type) {
            return plans.stream().filter(plan -> plan.getType() == type).findFirst();
        }

        @Override
        public List<Plan> findAll() {
            return plans;
        }

        @Override
        public Plan save(Plan plan) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long count() {
            return plans.size();
        }
    }
}
