package com.techwatch.techwatchbackend.subscriptions.infrastructure.seeding;

import com.techwatch.techwatchbackend.subscriptions.domain.model.aggregates.Plan;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.BillingCycle;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.Money;
import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanType;
import com.techwatch.techwatchbackend.subscriptions.domain.repositories.PlanRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Seeds the freemium plans catalog on application startup when the table is empty.
 */
@Component
public class PlanDataSeeder {

    private final PlanRepository planRepository;

    public PlanDataSeeder(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seedPlans() {
        if (planRepository.count() > 0) return;
        planRepository.save(buildPlan("Free", PlanType.FREE, new Money(0.0, "USD"), 5,
                false, false, true, false));
        planRepository.save(buildPlan("Premium", PlanType.PREMIUM, new Money(9.99, "USD"), 50,
                true, true, true, true));
    }

    private Plan buildPlan(String name, PlanType type, Money price, Integer maxDevices,
                           Boolean hasAdvancedMetrics, Boolean hasCustomReports,
                           Boolean hasAlerts, Boolean hasUnlimitedHistory) {
        var plan = new Plan();
        plan.setName(name);
        plan.setType(type);
        plan.setPrice(price);
        plan.setBillingCycle(BillingCycle.MONTHLY);
        plan.setMaxDevices(maxDevices);
        plan.setHasAdvancedMetrics(hasAdvancedMetrics);
        plan.setHasCustomReports(hasCustomReports);
        plan.setHasAlerts(hasAlerts);
        plan.setHasUnlimitedHistory(hasUnlimitedHistory);
        plan.setIsActive(true);
        return plan;
    }
}
