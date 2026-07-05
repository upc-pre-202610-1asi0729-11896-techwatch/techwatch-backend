package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionCancelledEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionPlanChangedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionRenewedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanId;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTests {

    private Subscription buildSubscription(Long id, Long planId, LocalDateTime endDate, SubscriptionStatus status) {
        var subscription = new Subscription();
        subscription.setId(id);
        subscription.setUserId(new UserId(5L));
        subscription.setPlanId(new PlanId(planId));
        subscription.setStartDate(LocalDateTime.now().minusMonths(1));
        subscription.setEndDate(endDate);
        subscription.setStatus(status);
        subscription.setAutoRenew(false);
        return subscription;
    }

    @Test
    void renewActiveSubscriptionExtendsFromCurrentEndDate() {
        var currentEndDate = LocalDateTime.now().plusDays(10);
        var subscription = buildSubscription(1L, 1L, currentEndDate, SubscriptionStatus.ACTIVE);

        subscription.renew(1);

        assertEquals(currentEndDate.plusMonths(1), subscription.getEndDate());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionRenewedEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void renewExpiredSubscriptionExtendsFromNow() {
        var subscription = buildSubscription(2L, 1L, LocalDateTime.now().minusDays(5), SubscriptionStatus.EXPIRED);

        subscription.renew(2);

        assertTrue(subscription.getEndDate().isAfter(LocalDateTime.now().plusMonths(2).minusMinutes(1)));
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
    }

    @Test
    void renewCancelledSubscriptionThrowsException() {
        var subscription = buildSubscription(3L, 1L, LocalDateTime.now().plusDays(5), SubscriptionStatus.CANCELLED);

        var exception = assertThrows(IllegalStateException.class, () -> subscription.renew(1));

        assertEquals("Cancelled subscriptions cannot be renewed", exception.getMessage());
    }

    @Test
    void cancelActiveSubscriptionChangesStatusToCancelled() {
        var subscription = buildSubscription(4L, 1L, LocalDateTime.now().plusDays(10), SubscriptionStatus.ACTIVE);

        subscription.cancel();

        assertEquals(SubscriptionStatus.CANCELLED, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionCancelledEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void cancelAlreadyCancelledSubscriptionThrowsException() {
        var subscription = buildSubscription(5L, 1L, LocalDateTime.now().plusDays(10), SubscriptionStatus.CANCELLED);

        var exception = assertThrows(IllegalStateException.class, subscription::cancel);

        assertEquals("Subscription is already cancelled", exception.getMessage());
    }

    @Test
    void cancelExpiredSubscriptionThrowsException() {
        var subscription = buildSubscription(6L, 1L, LocalDateTime.now().minusDays(5), SubscriptionStatus.EXPIRED);

        var exception = assertThrows(IllegalStateException.class, subscription::cancel);

        assertEquals("Expired subscriptions cannot be cancelled", exception.getMessage());
    }

    @Test
    void changeActiveSubscriptionPlanUpdatesPlanId() {
        var subscription = buildSubscription(7L, 1L, LocalDateTime.now().plusDays(10), SubscriptionStatus.ACTIVE);

        subscription.changePlan(new PlanId(2L));

        assertEquals(new PlanId(2L), subscription.getPlanId());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionPlanChangedEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void changeSubscriptionPlanToSamePlanThrowsException() {
        var subscription = buildSubscription(8L, 2L, LocalDateTime.now().plusDays(10), SubscriptionStatus.ACTIVE);

        var exception = assertThrows(IllegalStateException.class, () -> subscription.changePlan(new PlanId(2L)));

        assertEquals("Subscription already has this plan", exception.getMessage());
    }

    @Test
    void changeCancelledSubscriptionPlanThrowsException() {
        var subscription = buildSubscription(9L, 1L, LocalDateTime.now().plusDays(10), SubscriptionStatus.CANCELLED);

        var exception = assertThrows(IllegalStateException.class, () -> subscription.changePlan(new PlanId(2L)));

        assertEquals("Cancelled subscriptions cannot change plan", exception.getMessage());
    }

    @Test
    void changeExpiredSubscriptionPlanThrowsException() {
        var subscription = buildSubscription(10L, 1L, LocalDateTime.now().minusDays(5), SubscriptionStatus.EXPIRED);

        var exception = assertThrows(IllegalStateException.class, () -> subscription.changePlan(new PlanId(2L)));

        assertEquals("Expired subscriptions cannot change plan", exception.getMessage());
    }
}
