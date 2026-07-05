package com.techwatch.techwatchbackend.devices.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.devices.domain.model.events.SimulationSessionEndedEvent;
import com.techwatch.techwatchbackend.devices.interfaces.events.SimulationSessionEndedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link SimulationSessionEndedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link SimulationSessionEndedIntegrationEvent} and
 * re-publishes it on the Spring event bus. Other bounded contexts (e.g. {@code analytics}) must
 * subscribe to {@link SimulationSessionEndedIntegrationEvent}, never to the internal domain event.</p>
 */
@Service("devicesSimulationSessionEndedEventHandler")
public class SimulationSessionEndedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SimulationSessionEndedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SimulationSessionEndedEvent event) {
        eventPublisher.publishEvent(SimulationSessionEndedIntegrationEvent.from(event));
    }
}
