package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techwatch.techwatchbackend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.techwatch.techwatchbackend.subscriptions.domain.model.events.SubscriptionCreatedEvent;
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

    public Subscription cancel() {
        this.status = SubscriptionStatus.CANCELLED;
        return this;
    }
}
