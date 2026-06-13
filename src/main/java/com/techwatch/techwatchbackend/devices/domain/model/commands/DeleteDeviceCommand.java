package com.techwatch.techwatchbackend.devices.domain.model.commands;

/**
 * Command to delete a device.
 *
 * @param deviceId The id of the device to delete. Cannot be null or less than 1.
 */
public record DeleteDeviceCommand(Long deviceId) {
    /**
     * Compact constructor for DeleteDeviceCommand.
     * @throws IllegalArgumentException if the deviceId is null or less than 1.
     */
    public DeleteDeviceCommand {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
    }
}
