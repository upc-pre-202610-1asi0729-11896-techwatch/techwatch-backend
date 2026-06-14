package com.techwatch.techwatchbackend.analytics.domain.model.commands;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.MetricType;

import java.time.LocalDateTime;

/**
 * Command to calculate (record) a consumption metric.
 *
 * <p>Normally produced by the {@code UsageDataGeneratedEventHandler} in reaction to usage data
 * generated in the Device Management context.</p>
 *
 * @param propertyId The id of the property the metric belongs to. Cannot be null or less than 1.
 * @param deviceId The id of the device the metric belongs to. Cannot be null or less than 1.
 * @param metricType The type of metric. Cannot be null.
 * @param value The metric value. Cannot be null or negative.
 * @param unit The unit of the value. Cannot be null or blank.
 * @param recordedAt The moment the underlying data was recorded. Cannot be null.
 */
public record CalculateMetricsCommand(
        Long propertyId,
        Long deviceId,
        MetricType metricType,
        Double value,
        String unit,
        LocalDateTime recordedAt) {
    /**
     * Compact constructor for CalculateMetricsCommand.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public CalculateMetricsCommand {
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
        if (metricType == null) {
            throw new IllegalArgumentException("metricType cannot be null");
        }
        if (value == null || value < 0) {
            throw new IllegalArgumentException("value cannot be null or negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit cannot be null or blank");
        }
        if (recordedAt == null) {
            throw new IllegalArgumentException("recordedAt cannot be null");
        }
    }
}
