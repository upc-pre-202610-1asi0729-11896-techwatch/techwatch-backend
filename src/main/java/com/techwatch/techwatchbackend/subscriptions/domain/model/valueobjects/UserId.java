package com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects;

public record UserId(Long userId) {
    public UserId {
        if (userId == null || userId < 1) {
            throw new IllegalArgumentException("User id cannot be null or less than 1");
        }
    }
}
