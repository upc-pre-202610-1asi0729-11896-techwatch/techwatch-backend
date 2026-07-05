package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionCancelledEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionCreatedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionPlanChangedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionRenewedEvent;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanId;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.UserId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Setter
    private Long id;

    @Setter
    private UserId userId;

    @Setter
    private PlanId planId;

    @Setter
    private SubscriptionStatus status;

    @Setter
    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    @Setter
    private Boolean autoRenew;

    public Subscription() {
    }

    public Subscription(CreateSubscriptionCommand command) {
        this.userId = new UserId(command.userId());
        this.planId = new PlanId(command.planId());
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = LocalDateTime.now();
        this.autoRenew = command.autoRenew();
        registerDomainEvent(new SubscriptionCreatedEvent(null, command.userId(), command.planId(), command.autoRenew()));
    }

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE;
    }

    public boolean isExpired() {
        return this.status == SubscriptionStatus.EXPIRED;
    }

    public boolean isCancelled() {
        return this.status == SubscriptionStatus.CANCELLED;
    }

    public Subscription renew(Integer months) {
        if (this.isCancelled()) {
            throw new IllegalStateException("Cancelled subscriptions cannot be renewed");
        }
        var now = LocalDateTime.now();
        var baseDate = this.endDate != null && this.endDate.isAfter(now) ? this.endDate : now;
        this.endDate = baseDate.plusMonths(months);
        this.status = SubscriptionStatus.ACTIVE;
        registerDomainEvent(new SubscriptionRenewedEvent(this.id, this.endDate));
        return this;
    }

    public Subscription cancel() {
        if (this.isCancelled()) {
            throw new IllegalStateException("Subscription is already cancelled");
        }
        if (this.isExpired()) {
            throw new IllegalStateException("Expired subscriptions cannot be cancelled");
        }
        this.status = SubscriptionStatus.CANCELLED;
        registerDomainEvent(new SubscriptionCancelledEvent(this.id, LocalDateTime.now()));
        return this;
    }

    public Subscription changePlan(PlanId newPlanId) {
        if (this.isCancelled()) {
            throw new IllegalStateException("Cancelled subscriptions cannot change plan");
        }
        if (this.isExpired()) {
            throw new IllegalStateException("Expired subscriptions cannot change plan");
        }
        if (newPlanId.equals(this.planId)) {
            throw new IllegalStateException("Subscription already has this plan");
        }
        this.planId = newPlanId;
        registerDomainEvent(new SubscriptionPlanChangedEvent(this.id, newPlanId.planId(), LocalDateTime.now()));
        return this;
    }
}
