package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Space resource.
 */
@Schema(name = "SpaceResponse", description = "Space information response")
public record SpaceResource(
        @Schema(description = "Space unique identifier", example = "1")
        Long id,

        @Schema(description = "Space name", example = "Living Room")
        String name,

        @Schema(description = "Space description", example = "Main floor living room")
        String description
) {
}
