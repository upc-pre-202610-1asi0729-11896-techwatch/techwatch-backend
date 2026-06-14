package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SessionStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.SimulationSessionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for simulation session persistence entities.
 */
@Repository
public interface SimulationSessionPersistenceRepository extends JpaRepository<SimulationSessionPersistenceEntity, Long> {
    Optional<SimulationSessionPersistenceEntity> findFirstByUserIdAndStatus(UserId userId, SessionStatus status);
    boolean existsByUserIdAndStatus(UserId userId, SessionStatus status);
}
