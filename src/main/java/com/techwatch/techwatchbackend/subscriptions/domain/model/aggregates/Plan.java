package com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates;

import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.BillingCycle;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plan extends AbstractDomainAggregateRoot<Plan> {

    private Long id;

    private String name;

    private PlanType type;

    private Money price;

    private BillingCycle billingCycle;

    private Integer maxDevices;

    private Boolean hasAdvancedMetrics;

    private Boolean hasCustomReports;

    private Boolean hasAlerts;

    private Boolean hasUnlimitedHistory;

    private Boolean isActive;

    public Plan() {
    }

    public boolean isFree() {
        return this.price.amount() == 0;
    }
}
