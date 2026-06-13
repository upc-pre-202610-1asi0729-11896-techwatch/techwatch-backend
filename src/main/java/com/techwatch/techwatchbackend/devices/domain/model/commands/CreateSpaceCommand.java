package com.techwatch.techwatchbackend.devices.domain.model.commands;

/**
 * Command to create a new space within a property.
 *
 * @param propertyId The id of the property the space belongs to. Cannot be null or less than 1.
 * @param name The name of the space. Cannot be null or blank.
 * @param description The description of the space. Optional.
 */
public record CreateSpaceCommand(Long propertyId, String name, String description) {
    /**
     * Compact constructor for CreateSpaceCommand.
     * Validates the input data.
     * @throws IllegalArgumentException if propertyId or name is invalid.
     */
    public CreateSpaceCommand {
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
    }
}
