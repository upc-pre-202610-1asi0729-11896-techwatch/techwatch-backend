package com.techwatch.techwatchbackend.profiles.application.internal.queryservices;

import com.techwatch.techwatchbackend.profiles.application.queryservices.ProfileQueryService;
import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.techwatch.techwatchbackend.profiles.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {
        return profileRepository.findByUserId(query.userId());
    }
}
