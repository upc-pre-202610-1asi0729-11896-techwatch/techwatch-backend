package com.techwatch.techwatchbackend.analytics.domain.model.valueobjects;

/**
 * Value object representing the id of a device.
 *
 * <p>Cross-context reference to the device a metric belongs to (owned by Device Management).
 * It must be a positive Long value.</p>
 *
 * @param deviceId The device id. It cannot be null or less than 1.
 */
public record DeviceId(Long deviceId) {
    /**
     * Compact constructor for DeviceId.
     * @throws IllegalArgumentException if the deviceId is null or less than 1.
     */
    public DeviceId {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("Device id cannot be null or less than 1");
        }
    }
}
