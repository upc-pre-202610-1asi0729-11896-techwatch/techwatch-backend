package com.techwatch.techwatchbackend.devices.domain.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;

import java.util.Optional;

/**
 * Simulation session repository port.
 */
public interface SimulationSessionRepository {
    Optional<SimulationSession> findById(Long id);
    Optional<SimulationSession> findActiveByUserId(UserId userId);
    boolean existsActiveByUserId(UserId userId);
    SimulationSession save(SimulationSession session);
    boolean existsById(Long id);
}
