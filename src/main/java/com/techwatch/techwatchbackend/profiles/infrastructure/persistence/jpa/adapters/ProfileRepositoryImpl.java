package com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.profiles.domain.model.aggregates.Profile;
import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.domain.repositories.ProfileRepository;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository profilePersistenceRepository;

    public ProfileRepositoryImpl(ProfilePersistenceRepository profilePersistenceRepository) {
        this.profilePersistenceRepository = profilePersistenceRepository;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profilePersistenceRepository.findById(id)
                .map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Profile> findByUserId(UserId userId) {
        return profilePersistenceRepository.findByUserId(userId)
                .map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return profilePersistenceRepository.existsByUserId(userId);
    }

    @Override
    public Profile save(Profile profile) {
        var saved = profilePersistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        return ProfilePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
