package com.techwatch.techwatchbackend.analytics.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.analytics.application.commandservices.AnalyticsCommandService;
import com.techwatch.techwatchbackend.analytics.domain.model.commands.GenerateConsumptionReportCommand;
import com.techwatch.techwatchbackend.devices.interfaces.events.SimulationSessionEndedIntegrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Analytics event handler that reacts to a simulation session ending in the Device Management context.
 *
 * <p>Subscribes to the {@link SimulationSessionEndedIntegrationEvent} (the published language of the
 * {@code devices} context) and automatically generates a consumption report for the session's period.</p>
 */
@Service("analyticsSimulationSessionEndedEventHandler")
public class SimulationSessionEndedEventHandler {

    private final AnalyticsCommandService analyticsCommandService;

    public SimulationSessionEndedEventHandler(AnalyticsCommandService analyticsCommandService) {
        this.analyticsCommandService = analyticsCommandService;
    }

    @EventListener
    public void on(SimulationSessionEndedIntegrationEvent event) {
        analyticsCommandService.handle(new GenerateConsumptionReportCommand(
                event.userId(), event.propertyId(), event.startedAt(), event.endedAt(), true));
    }
}
