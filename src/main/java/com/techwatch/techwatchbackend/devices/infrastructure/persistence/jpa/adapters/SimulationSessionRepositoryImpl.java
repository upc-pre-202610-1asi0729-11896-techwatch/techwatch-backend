package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SessionStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.SimulationSessionRepository;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers.SimulationSessionPersistenceAssembler;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories.SimulationSessionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository adapter that bridges the simulation session domain repository port with Spring Data JPA.
 */
@Repository
public class SimulationSessionRepositoryImpl implements SimulationSessionRepository {

    private final SimulationSessionPersistenceRepository simulationSessionPersistenceRepository;

    public SimulationSessionRepositoryImpl(SimulationSessionPersistenceRepository simulationSessionPersistenceRepository) {
        this.simulationSessionPersistenceRepository = simulationSessionPersistenceRepository;
    }

    @Override
    public Optional<SimulationSession> findById(Long id) {
        return simulationSessionPersistenceRepository.findById(id).map(SimulationSessionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SimulationSession> findActiveByUserId(UserId userId) {
        return simulationSessionPersistenceRepository.findFirstByUserIdAndStatus(userId, SessionStatus.ACTIVE)
                .map(SimulationSessionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsActiveByUserId(UserId userId) {
        return simulationSessionPersistenceRepository.existsByUserIdAndStatus(userId, SessionStatus.ACTIVE);
    }

    @Override
    public SimulationSession save(SimulationSession session) {
        var saved = simulationSessionPersistenceRepository.save(SimulationSessionPersistenceAssembler.toPersistenceFromDomain(session));
        return SimulationSessionPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return simulationSessionPersistenceRepository.existsById(id);
    }
}
