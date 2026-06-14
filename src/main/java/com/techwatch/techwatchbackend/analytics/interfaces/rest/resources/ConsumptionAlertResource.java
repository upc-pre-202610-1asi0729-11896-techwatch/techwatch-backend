package com.techwatch.techwatchbackend.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Consumption alert resource.
 */
@Schema(name = "ConsumptionAlertResponse", description = "Consumption alert information response")
public record ConsumptionAlertResource(
        @Schema(description = "Alert unique identifier", example = "1")
        Long id,

        @Schema(description = "User id the alert is for", example = "1")
        Long userId,

        @Schema(description = "Property id the alert relates to", example = "1")
        Long propertyId,

        @Schema(description = "Device id the alert relates to", example = "1")
        Long deviceId,

        @Schema(description = "Alert severity", example = "HIGH")
        String severity,

        @Schema(description = "Human-readable alert message")
        String message,

        @Schema(description = "Threshold that was exceeded", example = "300.0")
        Double threshold,

        @Schema(description = "Consumption value that triggered the alert", example = "350.0")
        Double currentValue,

        @Schema(description = "Whether the alert has been read", example = "false")
        Boolean isRead,

        @Schema(description = "Moment the alert was triggered")
        LocalDateTime triggeredAt
) {
}
