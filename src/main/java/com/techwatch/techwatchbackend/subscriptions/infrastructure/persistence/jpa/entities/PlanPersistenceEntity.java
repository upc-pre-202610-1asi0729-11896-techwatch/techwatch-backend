package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.BillingCycle;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
public class PlanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false)
    private PlanType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency", nullable = false))
    })
    private MoneyPersistenceEmbeddable price;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_cycle", nullable = false)
    private BillingCycle billingCycle;

    @Column(name = "max_devices", nullable = false)
    private Integer maxDevices;

    @Column(name = "has_advanced_metrics", nullable = false)
    private Boolean hasAdvancedMetrics;

    @Column(name = "has_custom_reports", nullable = false)
    private Boolean hasCustomReports;

    @Column(name = "has_alerts", nullable = false)
    private Boolean hasAlerts;

    @Column(name = "has_unlimited_history", nullable = false)
    private Boolean hasUnlimitedHistory;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
