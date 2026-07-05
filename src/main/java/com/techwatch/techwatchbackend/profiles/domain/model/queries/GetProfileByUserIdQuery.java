package com.techwatch.techwatchbackend.profiles.domain.model.queries;

import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;

public record GetProfileByUserIdQuery(UserId userId) {
    public GetProfileByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
