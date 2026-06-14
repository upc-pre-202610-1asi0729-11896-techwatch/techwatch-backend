package com.techwatch.techwatchbackend.devices.domain.model.queries;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;

/**
 * Query to retrieve the active simulation session of a given user, if any.
 *
 * @param userId The id of the user. Cannot be null.
 */
public record GetActiveSimulationSessionByUserIdQuery(UserId userId) {
    /**
     * Compact constructor for GetActiveSimulationSessionByUserIdQuery.
     * @throws IllegalArgumentException if the userId is null.
     */
    public GetActiveSimulationSessionByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
