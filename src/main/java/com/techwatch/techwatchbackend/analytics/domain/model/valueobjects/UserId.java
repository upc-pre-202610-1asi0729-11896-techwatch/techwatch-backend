package com.techwatch.techwatchbackend.analytics.domain.model.valueobjects;

/**
 * Value object representing the id of a user.
 *
 * <p>Cross-context reference to the user an alert or report belongs to (owned by IAM).
 * It must be a positive Long value.</p>
 *
 * @param userId The user id. It cannot be null or less than 1.
 */
public record UserId(Long userId) {
    /**
     * Compact constructor for UserId.
     * @throws IllegalArgumentException if the userId is null or less than 1.
     */
    public UserId {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("User id cannot be null or less than 1");
        }
    }
}
