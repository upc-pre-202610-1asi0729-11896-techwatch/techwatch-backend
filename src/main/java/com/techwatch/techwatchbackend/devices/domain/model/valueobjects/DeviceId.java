package com.techwatch.techwatchbackend.devices.domain.model.valueobjects;

/**
 * Value object representing the id of a device.
 *
 * <p>
 * Used by the {@code SimulationSession} aggregate (in its device actions and usage data records)
 * to reference a device without holding a direct association to the {@code Device} aggregate.
 * It must be a positive Long value.
 * </p>
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
