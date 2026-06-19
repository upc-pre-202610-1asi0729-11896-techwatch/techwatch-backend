package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

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
}