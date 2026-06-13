package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Edit device resource.
 */
@Schema(
        name = "EditDeviceRequest",
        description = "Request payload for editing the descriptive information of a device",
        example = "{\"name\": \"Ceiling Light\", \"brand\": \"Philips\", \"model\": \"Hue White\", \"type\": \"LIGHT\", \"powerWatts\": 10.0}"
)
public record EditDeviceResource(
        @Schema(description = "Device name", example = "Ceiling Light", minLength = 1, maxLength = 255)
        String name,

        @Schema(description = "Device brand", example = "Philips", maxLength = 255)
        String brand,

        @Schema(description = "Device model", example = "Hue White", maxLength = 255)
        String model,

        @Schema(description = "Device type", example = "LIGHT",
                allowableValues = {"LIGHT", "THERMOSTAT", "CAMERA", "SMART_PLUG", "AIR_CONDITIONER", "DOOR_LOCK"})
        String type,

        @Schema(description = "Rated power consumption in watts", example = "10.0")
        Double powerWatts
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null/blank or invalid.
     */
    public EditDeviceResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type is required");
        }
        if (powerWatts == null) {
            throw new IllegalArgumentException("powerWatts is required");
        }
    }
}
