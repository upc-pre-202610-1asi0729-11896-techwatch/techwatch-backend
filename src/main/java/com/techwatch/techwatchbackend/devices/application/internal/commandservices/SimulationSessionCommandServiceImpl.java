package com.techwatch.techwatchbackend.devices.application.internal.commandservices;

import com.techwatch.techwatchbackend.devices.application.commandservices.SimulationSessionCommandService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.commands.EndSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.RecordDeviceActionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.StartSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
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
    private final DeviceRepository deviceRepository;

    public SimulationSessionCommandServiceImpl(SimulationSessionRepository simulationSessionRepository,
                                               DeviceRepository deviceRepository) {
        this.simulationSessionRepository = simulationSessionRepository;
        this.deviceRepository = deviceRepository;
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

    @Override
    public Result<SimulationSession, ApplicationError> handle(RecordDeviceActionCommand command) {
        var sessionResult = simulationSessionRepository.findById(command.sessionId());
        if (sessionResult.isEmpty())
            return Result.failure(ApplicationError.notFound("SimulationSession", command.sessionId().toString()));
        var session = sessionResult.get();
        if (!session.isActive())
            return Result.failure(ApplicationError.businessRuleViolation("record-device-action",
                    "the simulation session is not active"));
        var deviceResult = deviceRepository.findById(command.deviceId());
        if (deviceResult.isEmpty())
            return Result.failure(ApplicationError.notFound("Device", command.deviceId().toString()));
        var device = deviceResult.get();
        double consumptionValue = device.getPowerWatts().value() * (command.durationMinutes() / 60.0);
        session.recordAction(new DeviceId(command.deviceId()), command.actionType(),
                command.parameterName(), command.parameterValue(), consumptionValue, "Wh");
        try {
            applyStatusChange(device, command.actionType());
            var saved = simulationSessionRepository.save(session);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("record-device-action", e.getMessage()));
        }
    }

    /**
     * Turns the device on or off when the action type represents a power toggle, persisting the
     * change so the device reflects its real status after the simulated action.
     *
     * @param device The target device.
     * @param actionType The type of action recorded (e.g. TURN_ON, TURN_OFF, SET_TEMPERATURE).
     */
    private void applyStatusChange(Device device, String actionType) {
        if ("TURN_ON".equalsIgnoreCase(actionType)) {
            deviceRepository.save(device.turnOn());
        } else if ("TURN_OFF".equalsIgnoreCase(actionType)) {
            deviceRepository.save(device.turnOff());
        }
    }

    @Override
    public Result<SimulationSession, ApplicationError> handle(EndSimulationSessionCommand command) {
        var result = simulationSessionRepository.findById(command.sessionId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("SimulationSession", command.sessionId().toString()));
        var session = result.get();
        if (!session.isActive())
            return Result.failure(ApplicationError.businessRuleViolation("end-simulation-session",
                    "the simulation session is already ended"));
        try {
            var saved = simulationSessionRepository.save(session.end());
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("end-simulation-session", e.getMessage()));
        }
    }
}
