package com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.profiles.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.profiles.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePersistenceRepository extends JpaRepository<ProfilePersistenceEntity, Long> {
    Optional<ProfilePersistenceEntity> findByUserId(UserId userId);
    boolean existsByUserId(UserId userId);
}
