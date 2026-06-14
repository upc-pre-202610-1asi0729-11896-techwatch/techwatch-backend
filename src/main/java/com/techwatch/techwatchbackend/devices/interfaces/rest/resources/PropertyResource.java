package com.techwatch.techwatchbackend.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Property resource.
 */
@Schema(name = "PropertyResponse", description = "Property information response")
public record PropertyResource(
        @Schema(description = "Property unique identifier", example = "1")
        Long id,

        @Schema(description = "Owner user id", example = "1")
        Long userId,

        @Schema(description = "Property name", example = "My House")
        String name,

        @Schema(description = "Property address", example = "742 Evergreen Terrace")
        String address,

        @Schema(description = "Property type", example = "HOUSE")
        String type,

        @Schema(description = "Spaces (ambients) of the property")
        List<SpaceResource> spaces
) {
}
