package com.techwatch.techwatchbackend.devices.domain.model.events;

import java.time.LocalDateTime;

/**
 * Domain event published when a simulation session ends.
 *
 * <p>
 * Consumed by the Analytics bounded context to automatically generate a consumption report for the
 * session's period. The payload uses primitive identifiers (not value objects) so consumers in other
 * bounded contexts do not depend on the device management domain model.
 * </p>
 *
 * @param sessionId The id of the ended session.
 * @param userId The id of the user who owns the session.
 * @param propertyId The id of the property the session ran on.
 * @param startedAt The moment the session started.
 * @param endedAt The moment the session ended.
 */
public record SimulationSessionEndedEvent(
        Long sessionId,
        Long userId,
        Long propertyId,
        LocalDateTime startedAt,
        LocalDateTime endedAt) {
}
