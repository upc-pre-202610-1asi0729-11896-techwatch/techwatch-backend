package com.techwatch.techwatchbackend.devices.domain.model.valueobjects;

/**
 * Value object representing the id of a property.
 *
 * <p>
 * Used by the {@code SimulationSession} aggregate to reference the property the session runs on,
 * without holding a direct association to the {@code Property} aggregate. It must be a positive Long value.
 * </p>
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
