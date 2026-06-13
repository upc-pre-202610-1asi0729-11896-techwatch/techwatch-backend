package com.techwatch.techwatchbackend.devices.application.internal.queryservices;

import com.techwatch.techwatchbackend.devices.application.queryservices.SimulationSessionQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetActiveSimulationSessionByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetSimulationSessionByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.repositories.SimulationSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service that resolves simulation session read queries.
 */
@Service
public class SimulationSessionQueryServiceImpl implements SimulationSessionQueryService {
    private final SimulationSessionRepository simulationSessionRepository;

    public SimulationSessionQueryServiceImpl(SimulationSessionRepository simulationSessionRepository) {
        this.simulationSessionRepository = simulationSessionRepository;
    }

    @Override
    public Optional<SimulationSession> handle(GetSimulationSessionByIdQuery query) {
        return simulationSessionRepository.findById(query.sessionId());
    }

    @Override
    public Optional<SimulationSession> handle(GetActiveSimulationSessionByUserIdQuery query) {
        return simulationSessionRepository.findActiveByUserId(query.userId());
    }
}
