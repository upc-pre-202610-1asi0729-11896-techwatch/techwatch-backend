package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create device resource.
 */
@Schema(
        name = "CreateDeviceRequest",
        description = "Request payload for adding a new device to a space",
        example = "{\"spaceId\": 1, \"name\": \"Ceiling Light\", \"brand\": \"Philips\", \"model\": \"Hue\", \"type\": \"LIGHT\", \"powerWatts\": 9.5}"
)
public record CreateDeviceResource(
        @Schema(description = "Owning space id", example = "1")
        Long spaceId,

        @Schema(description = "Device name", example = "Ceiling Light", minLength = 1, maxLength = 255)
        String name,

        @Schema(description = "Device brand", example = "Philips", maxLength = 255)
        String brand,

        @Schema(description = "Device model", example = "Hue", maxLength = 255)
        String model,

        @Schema(description = "Device type", example = "LIGHT",
                allowableValues = {"LIGHT", "THERMOSTAT", "CAMERA", "SMART_PLUG", "AIR_CONDITIONER", "DOOR_LOCK"})
        String type,

        @Schema(description = "Rated power consumption in watts", example = "9.5")
        Double powerWatts
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null/blank or invalid.
     */
    public CreateDeviceResource {
        if (spaceId == null) {
            throw new IllegalArgumentException("spaceId is required");
        }
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
