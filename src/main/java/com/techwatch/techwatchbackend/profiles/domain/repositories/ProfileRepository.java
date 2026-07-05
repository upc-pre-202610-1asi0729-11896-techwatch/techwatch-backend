package com.techwatch.techwatchbackend.profiles.domain.repositories;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;

import java.util.Optional;

public interface ProfileRepository {
    Optional<Profile> findById(Long id);
    Optional<Profile> findByUserId(UserId userId);
    boolean existsByUserId(UserId userId);
    Profile save(Profile profile);
}
