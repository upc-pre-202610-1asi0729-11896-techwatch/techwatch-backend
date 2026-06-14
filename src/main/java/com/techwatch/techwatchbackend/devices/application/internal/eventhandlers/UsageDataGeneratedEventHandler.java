package com.techwatch.techwatchbackend.devices.application.internal.eventhandlers;

import com.techwatch.techwatchbackend.devices.domain.model.events.UsageDataGeneratedEvent;
import com.techwatch.techwatchbackend.devices.interfaces.events.UsageDataGeneratedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link UsageDataGeneratedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link UsageDataGeneratedIntegrationEvent} and
 * re-publishes it on the Spring event bus. This is the only place where the domain event crosses
 * the boundary between the domain layer and the published language of the {@code devices} context.</p>
 *
 * <p>Other bounded contexts (e.g. {@code analytics}) must subscribe to
 * {@link UsageDataGeneratedIntegrationEvent}, never to the internal {@link UsageDataGeneratedEvent}.</p>
 */
@Service("devicesUsageDataGeneratedEventHandler")
public class UsageDataGeneratedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public UsageDataGeneratedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link UsageDataGeneratedEvent} and publishes the corresponding
     * {@link UsageDataGeneratedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event
     */
    @EventListener
    public void on(UsageDataGeneratedEvent event) {
        eventPublisher.publishEvent(UsageDataGeneratedIntegrationEvent.from(event));
    }
}
