package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Simulation session resource.
 */
@Schema(name = "SimulationSessionResponse", description = "Simulation session information response")
public record SimulationSessionResource(
        @Schema(description = "Simulation session unique identifier", example = "1")
        Long id,

        @Schema(description = "Owner user id", example = "1")
        Long userId,

        @Schema(description = "Property the session runs on", example = "1")
        Long propertyId,

        @Schema(description = "Session status", example = "ACTIVE")
        String status,

        @Schema(description = "Moment the session started")
        LocalDateTime startedAt,

        @Schema(description = "Moment the session ended, if it has ended")
        LocalDateTime endedAt,

        @Schema(description = "Actions executed during the session")
        List<DeviceActionResource> actions,

        @Schema(description = "Usage data records generated during the session")
        List<UsageDataResource> usageData
) {
}
