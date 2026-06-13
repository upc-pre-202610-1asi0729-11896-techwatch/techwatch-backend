package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create space resource.
 */
@Schema(
        name = "CreateSpaceRequest",
        description = "Request payload for creating a new space within a property",
        example = "{\"name\": \"Living Room\", \"description\": \"Main floor living room\"}"
)
public record CreateSpaceResource(
        @Schema(description = "Space name", example = "Living Room", minLength = 1, maxLength = 255)
        String name,

        @Schema(description = "Space description", example = "Main floor living room", maxLength = 255)
        String description
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if the name is null or blank.
     */
    public CreateSpaceResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
