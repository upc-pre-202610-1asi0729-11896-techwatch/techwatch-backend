package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create property resource.
 */
@Schema(
        name = "CreatePropertyRequest",
        description = "Request payload for registering a new property",
        example = "{\"userId\": 1, \"name\": \"My House\", \"address\": \"742 Evergreen Terrace\", \"type\": \"HOUSE\"}"
)
public record CreatePropertyResource(
        @Schema(description = "Owner user id", example = "1")
        Long userId,

        @Schema(description = "Property name", example = "My House", minLength = 1, maxLength = 255)
        String name,

        @Schema(description = "Property address", example = "742 Evergreen Terrace", minLength = 1, maxLength = 255)
        String address,

        @Schema(description = "Property type", example = "HOUSE", allowableValues = {"HOUSE", "APARTMENT"})
        String type
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any required field is null or blank.
     */
    public CreatePropertyResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address is required");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type is required");
        }
    }
}
