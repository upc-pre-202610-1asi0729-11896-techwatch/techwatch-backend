package com.techwatch.techwatchbackend.devices.domain.model.queries;

/**
 * Query to retrieve a device by its unique identifier.
 *
 * @param deviceId The id of the device. Cannot be null or less than 1.
 */
public record GetDeviceByIdQuery(Long deviceId) {
    /**
     * Compact constructor for GetDeviceByIdQuery.
     * @throws IllegalArgumentException if the deviceId is null or less than 1.
     */
    public GetDeviceByIdQuery {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
    }
}
