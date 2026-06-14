package com.techwatch.techwatchbackend.devices.interfaces.events;

import com.techwatch.techwatchbackend.devices.domain.model.events.UsageDataGeneratedEvent;

import java.time.LocalDateTime;

/**
 * Integration event published by the {@code devices} bounded context when usage data is generated
 * for a device during a simulation session.
 *
 * <p>This is the <em>published language</em> of the {@code devices} context. Other bounded contexts
 * (e.g. {@code analytics}) must listen to this event rather than to the internal domain event
 * {@link UsageDataGeneratedEvent}.</p>
 *
 * @param userId The id of the user who owns the session.
 * @param propertyId The id of the property the session runs on.
 * @param deviceId The id of the device the usage data belongs to.
 * @param consumptionValue The consumption value.
 * @param unit The unit of the consumption value.
 * @param recordedAt The moment the usage data was recorded.
 */
public record UsageDataGeneratedIntegrationEvent(
        Long userId,
        Long propertyId,
        Long deviceId,
        Double consumptionValue,
        String unit,
        LocalDateTime recordedAt) {

    /**
     * Convenience factory that builds the integration event from the internal domain event.
     *
     * @param event the internal domain event
     * @return a fully populated {@link UsageDataGeneratedIntegrationEvent}
     */
    public static UsageDataGeneratedIntegrationEvent from(UsageDataGeneratedEvent event) {
        return new UsageDataGeneratedIntegrationEvent(
                event.userId(),
                event.propertyId(),
                event.deviceId(),
                event.consumptionValue(),
                event.unit(),
                event.recordedAt());
    }
}
