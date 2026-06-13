package com.techwatch.techwatchbackend.devices.domain.model.queries;

/**
 * Query to retrieve a space by its owning property and its name.
 *
 * @param propertyId The id of the property. Cannot be null or less than 1.
 * @param name The name of the space. Cannot be null or blank.
 */
public record GetSpaceByPropertyIdAndNameQuery(Long propertyId, String name) {
    /**
     * Compact constructor for GetSpaceByPropertyIdAndNameQuery.
     * @throws IllegalArgumentException if propertyId or name is invalid.
     */
    public GetSpaceByPropertyIdAndNameQuery {
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
    }
}
