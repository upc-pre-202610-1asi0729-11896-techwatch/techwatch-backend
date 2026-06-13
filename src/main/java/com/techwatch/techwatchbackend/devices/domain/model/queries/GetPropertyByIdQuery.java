package com.techwatch.techwatchbackend.devices.domain.model.queries;

/**
 * Query to retrieve a property by its unique identifier.
 *
 * @param propertyId The id of the property. Cannot be null or less than 1.
 */
public record GetPropertyByIdQuery(Long propertyId) {
    /**
     * Compact constructor for GetPropertyByIdQuery.
     * @throws IllegalArgumentException if the propertyId is null or less than 1.
     */
    public GetPropertyByIdQuery {
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
    }
}
