package com.techwatch.techwatchbackend.analytics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Report item resource.
 */
@Schema(name = "ReportItemResponse", description = "A per-device line of a consumption report")
public record ReportItemResource(
        @Schema(description = "Report item unique identifier", example = "1")
        Long id,

        @Schema(description = "Device id", example = "1")
        Long deviceId,

        @Schema(description = "Device display name", example = "Device #1")
        String deviceName,

        @Schema(description = "Consumption attributed to the device", example = "120.0")
        Double consumption,

        @Schema(description = "Unit of the consumption", example = "Wh")
        String unit,

        @Schema(description = "Number of usage records during the period", example = "3")
        Integer usageFrequency
) {
}
