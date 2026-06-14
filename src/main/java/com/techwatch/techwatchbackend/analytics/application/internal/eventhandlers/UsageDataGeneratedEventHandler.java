package com.techwatch.techwatchbackend.analytics.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.TriggerConsumptionAlertCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.AlertSeverity;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.MetricType;
import com.techwatch.techwatchbackend.devices.interfaces.events.UsageDataGeneratedIntegrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Analytics event handler that reacts to usage data generated in the Device Management context.
 *
 * <p>Subscribes to the {@link UsageDataGeneratedIntegrationEvent} (the published language of the
 * {@code devices} context), calculates the corresponding energy-consumption metric and, applying the
 * threshold policy, triggers a consumption alert when the value exceeds the configured thresholds.</p>
 */
@Service("analyticsUsageDataGeneratedEventHandler")
public class UsageDataGeneratedEventHandler {

    private final AnalyticsCommandService analyticsCommandService;
    private final double lowThreshold;
    private final double mediumThreshold;
    private final double highThreshold;

    public UsageDataGeneratedEventHandler(
            AnalyticsCommandService analyticsCommandService,
            @Value("${analytics.alert.threshold.low:50}") double lowThreshold,
            @Value("${analytics.alert.threshold.medium:150}") double mediumThreshold,
            @Value("${analytics.alert.threshold.high:300}") double highThreshold) {
        this.analyticsCommandService = analyticsCommandService;
        this.lowThreshold = lowThreshold;
        this.mediumThreshold = mediumThreshold;
        this.highThreshold = highThreshold;
    }

    /**
     * Receives the integration event, calculates the metric and evaluates the alert threshold policy.
     *
     * @param event the usage data generated integration event
     */
    @EventListener
    public void on(UsageDataGeneratedIntegrationEvent event) {
        analyticsCommandService.handle(new CalculateMetricsCommand(
                event.propertyId(),
                event.deviceId(),
                MetricType.ENERGY_CONSUMPTION,
                event.consumptionValue(),
                event.unit(),
                event.recordedAt()));

        evaluateAlert(event);
    }

    /**
     * Applies the threshold policy: if the consumption value reaches a configured threshold,
     * triggers a consumption alert with the corresponding severity.
     */
    private void evaluateAlert(UsageDataGeneratedIntegrationEvent event) {
        double value = event.consumptionValue();
        AlertSeverity severity;
        double crossedThreshold;
        if (value >= highThreshold) {
            severity = AlertSeverity.HIGH;
            crossedThreshold = highThreshold;
        } else if (value >= mediumThreshold) {
            severity = AlertSeverity.MEDIUM;
            crossedThreshold = mediumThreshold;
        } else if (value >= lowThreshold) {
            severity = AlertSeverity.LOW;
            crossedThreshold = lowThreshold;
        } else {
            return; // below the lowest threshold: no alert
        }
        analyticsCommandService.handle(new TriggerConsumptionAlertCommand(
                event.userId(),
                event.propertyId(),
                event.deviceId(),
                severity,
                crossedThreshold,
                value));
    }
}
