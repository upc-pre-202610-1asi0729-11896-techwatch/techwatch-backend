package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionRenewedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Subscription aggregate root.
 */
@Getter
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Setter
    private Long id;

    @Setter
    private Long userId;

    @Setter
    private String planName;

    @Setter
    private LocalDate startDate;

    @Setter
    private LocalDate endDate;

    @Setter
    private SubscriptionStatus status;

    public Subscription() {
    }

    public boolean isCanceled() {
        return this.status == SubscriptionStatus.CANCELED;
    }

    public Subscription renew(Integer months) {
        if (this.isCanceled()) {
            throw new IllegalStateException("Canceled subscriptions cannot be renewed");
        }

        var today = LocalDate.now();

        var baseDate = this.endDate != null && this.endDate.isAfter(today)
                ? this.endDate
                : today;

        this.endDate = baseDate.plusMonths(months);
        this.status = SubscriptionStatus.ACTIVE;

        registerDomainEvent(new SubscriptionRenewedEvent(this.id, this.endDate));

        return this;
    }
}