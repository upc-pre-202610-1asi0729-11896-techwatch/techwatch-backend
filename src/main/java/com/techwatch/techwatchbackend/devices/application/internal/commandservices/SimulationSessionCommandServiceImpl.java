package com.techwatch.techwatchbackend.devices.application.internal.commandservices;

import com.techwatch.techwatchbackend.devices.application.commandservices.SimulationSessionCommandService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.commands.StartSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.SimulationSessionRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes simulation session commands.
 */
@Service
public class SimulationSessionCommandServiceImpl implements SimulationSessionCommandService {
    private final SimulationSessionRepository simulationSessionRepository;

    public SimulationSessionCommandServiceImpl(SimulationSessionRepository simulationSessionRepository) {
        this.simulationSessionRepository = simulationSessionRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(StartSimulationSessionCommand command) {
        if (simulationSessionRepository.existsActiveByUserId(new UserId(command.userId())))
            return Result.failure(ApplicationError.conflict("SimulationSession",
                    "User already has an active simulation session"));
        var session = new SimulationSession(command);
        try {
            session = simulationSessionRepository.save(session);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("start-simulation-session", e.getMessage()));
        }
        return Result.success(session.getId());
    }
}
