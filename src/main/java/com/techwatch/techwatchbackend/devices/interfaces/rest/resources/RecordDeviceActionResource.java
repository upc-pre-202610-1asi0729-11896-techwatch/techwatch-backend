package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record device action resource.
 */
@Schema(
        name = "RecordDeviceActionRequest",
        description = "Request payload for recording a device action during a simulation session",
        example = "{\"deviceId\": 1, \"actionType\": \"TURN_ON\", \"parameterName\": null, \"parameterValue\": null, \"durationMinutes\": 30}"
)
public record RecordDeviceActionResource(
        @Schema(description = "Target device id", example = "1")
        Long deviceId,

        @Schema(description = "Action type", example = "TURN_ON")
        String actionType,

        @Schema(description = "Parameter name, if any", example = "temperature")
        String parameterName,

        @Schema(description = "Parameter value, if any", example = "22")
        String parameterValue,

        @Schema(description = "Duration of the action in minutes (used to compute consumption)", example = "30")
        Double durationMinutes
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null/blank or invalid.
     */
    public RecordDeviceActionResource {
        if (deviceId == null) {
            throw new IllegalArgumentException("deviceId is required");
        }
        if (actionType == null || actionType.isBlank()) {
            throw new IllegalArgumentException("actionType is required");
        }
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new IllegalArgumentException("durationMinutes must be greater than 0");
        }
    }
}
