package com.techwatch.techwatchbackend.profiles.domain.model.queries;

public record GetProfileByIdQuery(Long profileId) {
    public GetProfileByIdQuery {
        if (profileId == null || profileId < 1) {
            throw new IllegalArgumentException("profileId cannot be null or less than 1");
        }
    }
}
