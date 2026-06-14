package com.techwatch.techwatchbackend.devices.domain.model.commands;

/**
 * Command to end an active simulation session.
 *
 * @param sessionId The id of the session to end. Cannot be null or less than 1.
 */
public record EndSimulationSessionCommand(Long sessionId) {
    /**
     * Compact constructor for EndSimulationSessionCommand.
     * @throws IllegalArgumentException if the sessionId is null or less than 1.
     */
    public EndSimulationSessionCommand {
        if (sessionId == null || sessionId < 1) {
            throw new IllegalArgumentException("sessionId cannot be null or less than 1");
        }
    }
}
