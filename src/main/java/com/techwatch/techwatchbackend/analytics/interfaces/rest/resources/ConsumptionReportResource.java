package com.techwatch.techwatchbackend.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Consumption report resource.
 */
@Schema(name = "ConsumptionReportResponse", description = "Consumption report information response")
public record ConsumptionReportResource(
        @Schema(description = "Report unique identifier", example = "1")
        Long id,

        @Schema(description = "User id the report is for", example = "1")
        Long userId,

        @Schema(description = "Property id the report covers", example = "1")
        Long propertyId,

        @Schema(description = "Start of the report period")
        LocalDateTime periodStart,

        @Schema(description = "End of the report period")
        LocalDateTime periodEnd,

        @Schema(description = "Total consumption over the period", example = "320.0")
        Double totalConsumption,

        @Schema(description = "Unit of the total consumption", example = "Wh")
        String unit,

        @Schema(description = "Moment the report was generated")
        LocalDateTime generatedAt,

        @Schema(description = "Per-device lines of the report")
        List<ReportItemResource> items
) {
}
