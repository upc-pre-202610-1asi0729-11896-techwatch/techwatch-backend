package com.techwatch.techwatchbackend.devices.domain.model.commands;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyType;

/**
 * Command to register a new property for a user.
 *
 * @param userId The id of the user who owns the property. Cannot be null or less than 1.
 * @param name The name of the property. Cannot be null or blank.
 * @param address The address of the property. Cannot be null or blank.
 * @param type The type of the property. Cannot be null.
 */
public record CreatePropertyCommand(Long userId, String name, String address, PropertyType type) {
    /**
     * Compact constructor for CreatePropertyCommand.
     * Validates the input data.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public CreatePropertyCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("address cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
    }
}
