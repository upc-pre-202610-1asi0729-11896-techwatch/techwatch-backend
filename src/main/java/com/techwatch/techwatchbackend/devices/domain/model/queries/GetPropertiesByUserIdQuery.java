package com.techwatch.techwatchbackend.devices.domain.model.queries;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;

/**
 * Query to retrieve all the properties owned by a given user.
 *
 * @param userId The id of the user. Cannot be null.
 */
public record GetPropertiesByUserIdQuery(UserId userId) {
    /**
     * Compact constructor for GetPropertiesByUserIdQuery.
     * @throws IllegalArgumentException if the userId is null.
     */
    public GetPropertiesByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
