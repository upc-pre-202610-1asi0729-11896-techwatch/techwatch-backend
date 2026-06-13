package com.techwatch.techwatchbackend.devices.domain.model.queries;

/**
 * Query to retrieve a simulation session by its unique identifier.
 *
 * @param sessionId The id of the session. Cannot be null or less than 1.
 */
public record GetSimulationSessionByIdQuery(Long sessionId) {
    /**
     * Compact constructor for GetSimulationSessionByIdQuery.
     * @throws IllegalArgumentException if the sessionId is null or less than 1.
     */
    public GetSimulationSessionByIdQuery {
        if (sessionId == null || sessionId < 1) {
            throw new IllegalArgumentException("sessionId cannot be null or less than 1");
        }
    }
}
