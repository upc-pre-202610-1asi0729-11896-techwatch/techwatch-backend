package com.techwatch.techwatchbackend.devices.domain.model.commands;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;

/**
 * Command to edit the descriptive information of an existing device.
 *
 * <p>The power status (ON/OFF) is not modified through this command; it is changed by the
 * device control / simulation flow.</p>
 *
 * @param deviceId The id of the device to edit. Cannot be null or less than 1.
 * @param name The new name of the device. Cannot be null or blank.
 * @param brand The new brand of the device. Optional.
 * @param model The new model of the device. Optional.
 * @param type The new type of the device. Cannot be null.
 * @param powerWatts The new rated power consumption, in watts. Cannot be null or negative.
 */
public record EditDeviceCommand(Long deviceId, String name, String brand, String model, DeviceType type, Double powerWatts) {
    /**
     * Compact constructor for EditDeviceCommand.
     * Validates the input data.
     * @throws IllegalArgumentException if any required field is invalid.
     */
    public EditDeviceCommand {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (powerWatts == null || powerWatts < 0) {
            throw new IllegalArgumentException("powerWatts cannot be null or negative");
        }
    }
}
