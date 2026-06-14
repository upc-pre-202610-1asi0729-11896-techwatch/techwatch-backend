package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SessionStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.SimulationSessionRepository;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers.SimulationSessionPersistenceAssembler;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories.SimulationSessionPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository adapter that bridges the simulation session domain repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a session is persisted, any domain events
 * registered on the aggregate (e.g. {@code UsageDataGeneratedEvent}) are dispatched via Spring's
 * {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class SimulationSessionRepositoryImpl implements SimulationSessionRepository {

    private final SimulationSessionPersistenceRepository simulationSessionPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SimulationSessionRepositoryImpl(SimulationSessionPersistenceRepository simulationSessionPersistenceRepository,
                                           ApplicationEventPublisher eventPublisher) {
        this.simulationSessionPersistenceRepository = simulationSessionPersistenceRepository;
        this.eventPublisher = eventPublisher;
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
        var result = SimulationSessionPersistenceAssembler.toDomainFromPersistence(saved);
        // Publish any domain events registered on the aggregate (e.g. during recordAction) once the
        // session has been persisted, then clear them.
        session.domainEvents().forEach(eventPublisher::publishEvent);
        session.clearDomainEvents();
        return result;
    }

    @Override
    public boolean existsById(Long id) {
        return simulationSessionPersistenceRepository.existsById(id);
    }
}
