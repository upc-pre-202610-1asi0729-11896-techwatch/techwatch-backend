package com.techwatch.techwatchbackend.analytics.domain.model.queries;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;

/**
 * Query to retrieve all the consumption alerts of a given user.
 *
 * @param userId The id of the user. Cannot be null.
 */
public record GetAlertsByUserIdQuery(UserId userId) {
    /**
     * Compact constructor for GetAlertsByUserIdQuery.
     * @throws IllegalArgumentException if the userId is null.
     */
    public GetAlertsByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
