package com.techwatch.techwatchbackend.analytics.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.MetricType;
import com.techwatch.techwatchbackend.devices.interfaces.events.UsageDataGeneratedIntegrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Analytics event handler that reacts to usage data generated in the Device Management context.
 *
 * <p>Subscribes to the {@link UsageDataGeneratedIntegrationEvent} (the published language of the
 * {@code devices} context) and calculates the corresponding energy-consumption metric.</p>
 */
@Service("analyticsUsageDataGeneratedEventHandler")
public class UsageDataGeneratedEventHandler {

    private final AnalyticsCommandService analyticsCommandService;

    public UsageDataGeneratedEventHandler(AnalyticsCommandService analyticsCommandService) {
        this.analyticsCommandService = analyticsCommandService;
    }

    /**
     * Receives the integration event and triggers the metric calculation.
     *
     * @param event the usage data generated integration event
     */
    @EventListener
    public void on(UsageDataGeneratedIntegrationEvent event) {
        var command = new CalculateMetricsCommand(
                event.propertyId(),
                event.deviceId(),
                MetricType.ENERGY_CONSUMPTION,
                event.consumptionValue(),
                event.unit(),
                event.recordedAt());
        analyticsCommandService.handle(command);
    }
}
