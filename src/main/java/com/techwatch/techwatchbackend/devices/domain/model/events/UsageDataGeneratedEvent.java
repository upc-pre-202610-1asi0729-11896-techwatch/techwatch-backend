package com.techwatch.techwatchbackend.devices.domain.model.events;

import java.time.LocalDateTime;

/**
 * Domain event published when usage data is generated for a device during a simulation session.
 *
 * <p>
 * It is consumed by the Analytics bounded context to calculate metrics and evaluate alert
 * thresholds. The payload uses primitive identifiers (not value objects) so consumers in other
 * bounded contexts do not depend on the device management domain model.
 * </p>
 *
 * @param userId The id of the user who owns the session.
 * @param propertyId The id of the property the session runs on.
 * @param deviceId The id of the device the usage data belongs to.
 * @param consumptionValue The consumption value.
 * @param unit The unit of the consumption value.
 * @param recordedAt The moment the usage data was recorded.
 */
public record UsageDataGeneratedEvent(
        Long userId,
        Long propertyId,
        Long deviceId,
        Double consumptionValue,
        String unit,
        LocalDateTime recordedAt) {
}
