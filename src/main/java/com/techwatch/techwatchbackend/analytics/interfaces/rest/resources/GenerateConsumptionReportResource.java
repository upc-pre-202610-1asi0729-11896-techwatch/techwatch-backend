package com.techwatch.techwatchbackend.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Generate consumption report resource.
 */
@Schema(
        name = "GenerateConsumptionReportRequest",
        description = "Request payload for generating a consumption report",
        example = "{\"userId\": 1, \"propertyId\": 1, \"startDate\": \"2026-06-01T00:00:00\", \"endDate\": \"2026-06-30T23:59:59\"}"
)
public record GenerateConsumptionReportResource(
        @Schema(description = "User id requesting the report", example = "1")
        Long userId,

        @Schema(description = "Property id the report covers", example = "1")
        Long propertyId,

        @Schema(description = "Start of the report period")
        LocalDateTime startDate,

        @Schema(description = "End of the report period")
        LocalDateTime endDate
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null or the period is invalid.
     */
    public GenerateConsumptionReportResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (propertyId == null) {
            throw new IllegalArgumentException("propertyId is required");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
    }
}
