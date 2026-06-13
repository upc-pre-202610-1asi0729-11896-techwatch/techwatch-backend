package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Device resource.
 */
@Schema(name = "DeviceResponse", description = "Device information response")
public record DeviceResource(
        @Schema(description = "Device unique identifier", example = "1")
        Long id,

        @Schema(description = "Owning space id", example = "1")
        Long spaceId,

        @Schema(description = "Device name", example = "Ceiling Light")
        String name,

        @Schema(description = "Device brand", example = "Philips")
        String brand,

        @Schema(description = "Device model", example = "Hue")
        String model,

        @Schema(description = "Device type", example = "LIGHT")
        String type,

        @Schema(description = "Device power status", example = "OFF")
        String status,

        @Schema(description = "Rated power consumption in watts", example = "9.5")
        Double powerWatts
) {
}
