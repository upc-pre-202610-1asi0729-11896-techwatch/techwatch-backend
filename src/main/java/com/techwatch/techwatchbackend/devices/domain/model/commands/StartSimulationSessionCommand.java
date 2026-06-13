package com.techwatch.techwatchbackend.devices.domain.model.commands;

/**
 * Command to start a new simulation session for a user on a property.
 *
 * @param userId The id of the user starting the session. Cannot be null or less than 1.
 * @param propertyId The id of the property the session runs on. Cannot be null or less than 1.
 */
public record StartSimulationSessionCommand(Long userId, Long propertyId) {
    /**
     * Compact constructor for StartSimulationSessionCommand.
     * @throws IllegalArgumentException if any field is invalid.
     */
    public StartSimulationSessionCommand {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
        if (propertyId == null || propertyId < 1) {
            throw new IllegalArgumentException("propertyId cannot be null or less than 1");
        }
    }
}
