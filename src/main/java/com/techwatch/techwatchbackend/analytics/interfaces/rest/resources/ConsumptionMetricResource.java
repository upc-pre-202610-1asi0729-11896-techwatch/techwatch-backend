package com.techwatch.techwatchbackend.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Consumption metric resource.
 */
@Schema(name = "ConsumptionMetricResponse", description = "Consumption metric information response")
public record ConsumptionMetricResource(
        @Schema(description = "Metric unique identifier", example = "1")
        Long id,

        @Schema(description = "Property id the metric belongs to", example = "1")
        Long propertyId,

        @Schema(description = "Device id the metric belongs to (null for property-level metrics)", example = "1")
        Long deviceId,

        @Schema(description = "Metric type", example = "ENERGY_CONSUMPTION")
        String metricType,

        @Schema(description = "Metric value", example = "50.0")
        Double value,

        @Schema(description = "Unit of the value", example = "Wh")
        String unit,

        @Schema(description = "Start of the period the metric covers")
        LocalDateTime periodStart,

        @Schema(description = "End of the period the metric covers")
        LocalDateTime periodEnd,

        @Schema(description = "Moment the metric was calculated")
        LocalDateTime calculatedAt
) {
}
