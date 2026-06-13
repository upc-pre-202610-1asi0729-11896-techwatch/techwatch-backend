package com.techwatch.techwatchbackend.devices.domain.model.valueobjects;

/**
 * Value object representing the rated power consumption of a device, expressed in watts.
 *
 * @param value The power in watts. It cannot be null or negative.
 */
public record PowerWatts(Double value) {
    /**
     * Compact constructor for PowerWatts.
     * @throws IllegalArgumentException if the value is null or negative.
     */
    public PowerWatts {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Power watts cannot be null or negative");
        }
    }
}
