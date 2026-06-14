package com.techwatch.techwatchbackend.analytics.domain.model.valueobjects;

/**
 * Value object representing the id of a property.
 *
 * <p>Cross-context reference to the property the metric belongs to (owned by Device Management).
 * It must be a positive Long value.</p>
 *
 * @param propertyId The property id. It cannot be null or less than 1.
 */
public record PropertyId(Long propertyId) {
    /**
     * Compact constructor for PropertyId.
     * @throws IllegalArgumentException if the propertyId is null or less than 1.
     */
    public PropertyId {
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("Property id cannot be null or less than 1");
        }
    }
}
