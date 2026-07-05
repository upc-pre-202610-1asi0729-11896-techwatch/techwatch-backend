package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionCanceledEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionPlanChangedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionRenewedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTests {

    @Test
    void renewActiveSubscriptionExtendsFromCurrentEndDate() {
        var subscription = new Subscription();
        var currentEndDate = LocalDate.now().plusDays(10);

        subscription.setId(1L);
        subscription.setUserId(5L);
        subscription.setPlanName("PREMIUM");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(currentEndDate);
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        subscription.renew(1);

        assertEquals(currentEndDate.plusMonths(1), subscription.getEndDate());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionRenewedEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void renewExpiredSubscriptionExtendsFromToday() {
        var subscription = new Subscription();

        subscription.setId(2L);
        subscription.setUserId(5L);
        subscription.setPlanName("BASIC");
        subscription.setStartDate(LocalDate.now().minusMonths(2));
        subscription.setEndDate(LocalDate.now().minusDays(5));
        subscription.setStatus(SubscriptionStatus.EXPIRED);

        subscription.renew(2);

        assertEquals(LocalDate.now().plusMonths(2), subscription.getEndDate());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
    }

    @Test
    void renewCanceledSubscriptionThrowsException() {
        var subscription = new Subscription();

        subscription.setId(3L);
        subscription.setUserId(5L);
        subscription.setPlanName("PREMIUM");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(5));
        subscription.setStatus(SubscriptionStatus.CANCELED);

        var exception = assertThrows(
                IllegalStateException.class,
                () -> subscription.renew(1)
        );

        assertEquals("Canceled subscriptions cannot be renewed", exception.getMessage());
    }

    @Test
    void cancelActiveSubscriptionChangesStatusToCanceled() {
        var subscription = new Subscription();

        subscription.setId(4L);
        subscription.setUserId(5L);
        subscription.setPlanName("PREMIUM");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(10));
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        subscription.cancel();

        assertEquals(SubscriptionStatus.CANCELED, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionCanceledEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void cancelAlreadyCanceledSubscriptionThrowsException() {
        var subscription = new Subscription();

        subscription.setId(5L);
        subscription.setUserId(5L);
        subscription.setPlanName("PREMIUM");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(10));
        subscription.setStatus(SubscriptionStatus.CANCELED);

        var exception = assertThrows(
                IllegalStateException.class,
                subscription::cancel
        );

        assertEquals("Subscription is already canceled", exception.getMessage());
    }

    @Test
    void cancelExpiredSubscriptionThrowsException() {
        var subscription = new Subscription();

        subscription.setId(6L);
        subscription.setUserId(5L);
        subscription.setPlanName("BASIC");
        subscription.setStartDate(LocalDate.now().minusMonths(2));
        subscription.setEndDate(LocalDate.now().minusDays(5));
        subscription.setStatus(SubscriptionStatus.EXPIRED);

        var exception = assertThrows(
                IllegalStateException.class,
                subscription::cancel
        );

        assertEquals("Expired subscriptions cannot be canceled", exception.getMessage());
    }

    @Test
    void changeActiveSubscriptionPlanUpdatesPlanName() {
        var subscription = new Subscription();

        subscription.setId(7L);
        subscription.setUserId(5L);
        subscription.setPlanName("BASIC");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(10));
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        subscription.changePlan("PREMIUM");

        assertEquals("PREMIUM", subscription.getPlanName());
        assertEquals(SubscriptionStatus.ACTIVE, subscription.getStatus());
        assertEquals(1, subscription.domainEvents().size());
        assertInstanceOf(SubscriptionPlanChangedEvent.class, subscription.domainEvents().iterator().next());
    }

    @Test
    void changeSubscriptionPlanToSamePlanThrowsException() {
        var subscription = new Subscription();

        subscription.setId(8L);
        subscription.setUserId(5L);
        subscription.setPlanName("PREMIUM");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(10));
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        var exception = assertThrows(
                IllegalStateException.class,
                () -> subscription.changePlan("PREMIUM")
        );

        assertEquals("Subscription already has this plan", exception.getMessage());
    }

    @Test
    void changeCanceledSubscriptionPlanThrowsException() {
        var subscription = new Subscription();

        subscription.setId(9L);
        subscription.setUserId(5L);
        subscription.setPlanName("BASIC");
        subscription.setStartDate(LocalDate.now().minusMonths(1));
        subscription.setEndDate(LocalDate.now().plusDays(10));
        subscription.setStatus(SubscriptionStatus.CANCELED);

        var exception = assertThrows(
                IllegalStateException.class,
                () -> subscription.changePlan("PREMIUM")
        );

        assertEquals("Canceled subscriptions cannot change plan", exception.getMessage());
    }

    @Test
    void changeExpiredSubscriptionPlanThrowsException() {
        var subscription = new Subscription();

        subscription.setId(10L);
        subscription.setUserId(5L);
        subscription.setPlanName("BASIC");
        subscription.setStartDate(LocalDate.now().minusMonths(2));
        subscription.setEndDate(LocalDate.now().minusDays(5));
        subscription.setStatus(SubscriptionStatus.EXPIRED);

        var exception = assertThrows(
                IllegalStateException.class,
                () -> subscription.changePlan("PREMIUM")
        );

        assertEquals("Expired subscriptions cannot change plan", exception.getMessage());
    }
}