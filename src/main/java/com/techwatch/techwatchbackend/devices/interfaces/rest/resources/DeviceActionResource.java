package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Device action resource.
 */
@Schema(name = "DeviceActionResponse", description = "A device action recorded during a simulation session")
public record DeviceActionResource(
        @Schema(description = "Device action unique identifier", example = "1")
        Long id,

        @Schema(description = "Target device id", example = "1")
        Long deviceId,

        @Schema(description = "Action type", example = "TURN_ON")
        String actionType,

        @Schema(description = "Parameter name, if any", example = "temperature")
        String parameterName,

        @Schema(description = "Parameter value, if any", example = "22")
        String parameterValue,

        @Schema(description = "Moment the action was executed")
        LocalDateTime executedAt
) {
}
