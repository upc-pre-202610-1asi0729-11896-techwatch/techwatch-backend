package com.techwatch.techwatchbackend.devices.interfaces.events;

import com.techwatch.techwatchbackend.devices.domain.model.events.SimulationSessionEndedEvent;

import java.time.LocalDateTime;

/**
 * Integration event published by the {@code devices} bounded context when a simulation session ends.
 *
 * <p>This is the <em>published language</em> of the {@code devices} context. Other bounded contexts
 * (e.g. {@code analytics}) must listen to this event rather than to the internal domain event
 * {@link SimulationSessionEndedEvent}.</p>
 *
 * @param sessionId The id of the ended session.
 * @param userId The id of the user who owns the session.
 * @param propertyId The id of the property the session ran on.
 * @param startedAt The moment the session started.
 * @param endedAt The moment the session ended.
 */
public record SimulationSessionEndedIntegrationEvent(
        Long sessionId,
        Long userId,
        Long propertyId,
        LocalDateTime startedAt,
        LocalDateTime endedAt) {

    public static SimulationSessionEndedIntegrationEvent from(SimulationSessionEndedEvent event) {
        return new SimulationSessionEndedIntegrationEvent(
                event.sessionId(), event.userId(), event.propertyId(), event.startedAt(), event.endedAt());
    }
}
