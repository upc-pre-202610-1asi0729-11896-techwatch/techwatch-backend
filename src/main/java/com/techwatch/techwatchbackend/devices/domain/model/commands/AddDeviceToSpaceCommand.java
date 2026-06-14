package com.techwatch.techwatchbackend.devices.domain.model.commands;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;

/**
 * Command to add a new device to a space.
 *
 * @param spaceId The id of the space the device belongs to. Cannot be null or less than 1.
 * @param name The name of the device. Cannot be null or blank.
 * @param brand The brand of the device. Optional.
 * @param model The model of the device. Optional.
 * @param type The type of the device. Cannot be null.
 * @param powerWatts The rated power consumption of the device, in watts. Cannot be null or negative.
 */
public record AddDeviceToSpaceCommand(Long spaceId, String name, String brand, String model, DeviceType type, Double powerWatts) {
    /**
     * Compact constructor for AddDeviceToSpaceCommand.
     * Validates the input data.
     * @throws IllegalArgumentException if any required field is invalid.
     */
    public AddDeviceToSpaceCommand {
        if (spaceId == null || spaceId < 1) {
            throw new IllegalArgumentException("spaceId cannot be null or less than 1");
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
