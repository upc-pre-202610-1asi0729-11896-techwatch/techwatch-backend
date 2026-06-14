package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Usage data resource.
 */
@Schema(name = "UsageDataResponse", description = "A usage data record generated during a simulation session")
public record UsageDataResource(
        @Schema(description = "Usage data record unique identifier", example = "1")
        Long id,

        @Schema(description = "Device id the usage data belongs to", example = "1")
        Long deviceId,

        @Schema(description = "Consumption value", example = "9.5")
        Double consumptionValue,

        @Schema(description = "Unit of the consumption value", example = "Wh")
        String unit,

        @Schema(description = "Moment the usage data was recorded")
        LocalDateTime recordedAt
) {
}
