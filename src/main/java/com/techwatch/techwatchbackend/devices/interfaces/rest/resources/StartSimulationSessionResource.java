package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Start simulation session resource.
 */
@Schema(
        name = "StartSimulationSessionRequest",
        description = "Request payload for starting a simulation session",
        example = "{\"userId\": 1, \"propertyId\": 1}"
)
public record StartSimulationSessionResource(
        @Schema(description = "User id starting the session", example = "1")
        Long userId,

        @Schema(description = "Property the session runs on", example = "1")
        Long propertyId
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null.
     */
    public StartSimulationSessionResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (propertyId == null) {
            throw new IllegalArgumentException("propertyId is required");
        }
    }
}
