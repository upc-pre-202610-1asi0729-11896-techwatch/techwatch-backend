package com.techwatch.techwatchbackend.profiles.application.queryservices;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByUserIdQuery;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    Optional<Profile> handle(GetProfileByUserIdQuery query);
}
