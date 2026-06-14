package com.techwatch.techwatchbackend.analytics.domain.model.commands;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.AlertSeverity;

/**
 * Command to trigger a consumption alert.
 *
 * <p>Normally produced by the {@code UsageDataGeneratedEventHandler} when a consumption value
 * exceeds a configured threshold.</p>
 *
 * @param userId The id of the user the alert is for. Cannot be null or less than 1.
 * @param propertyId The id of the property. Cannot be null or less than 1.
 * @param deviceId The id of the device. Cannot be null or less than 1.
 * @param severity The severity of the alert. Cannot be null.
 * @param threshold The threshold that was exceeded. Cannot be null or negative.
 * @param currentValue The consumption value that triggered the alert. Cannot be null or negative.
 */
public record TriggerConsumptionAlertCommand(
        Long userId,
        Long propertyId,
        Long deviceId,
        AlertSeverity severity,
        Double threshold,
        Double currentValue) {
    /**
     * Compact constructor for TriggerConsumptionAlertCommand.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public TriggerConsumptionAlertCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
        if (severity == null) {
            throw new IllegalArgumentException("severity cannot be null");
        }
        if (threshold == null || threshold < 0) {
            throw new IllegalArgumentException("threshold cannot be null or negative");
        }
        if (currentValue == null || currentValue < 0) {
            throw new IllegalArgumentException("currentValue cannot be null or negative");
        }
    }
}
