package com.techwatch.techwatchbackend.devices.domain.model.valueobjects;

/**
 * Value object representing the id of a space.
 *
 * <p>
 * Used by the {@code Device} aggregate to reference the space it belongs to without holding a
 * direct association to the {@code Property} aggregate. It must be a positive Long value.
 * </p>
 *
 * @param spaceId The space id. It cannot be null or less than 1.
 */
public record SpaceId(Long spaceId) {
    /**
     * Compact constructor for SpaceId.
     * @throws IllegalArgumentException if the spaceId is null or less than 1.
     */
    public SpaceId {
        if (spaceId == null || spaceId < 1) {
            throw new IllegalArgumentException("Space id cannot be null or less than 1");
        }
    }
}
