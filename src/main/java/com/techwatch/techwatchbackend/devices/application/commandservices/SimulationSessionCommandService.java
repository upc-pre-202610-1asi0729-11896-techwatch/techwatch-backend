package com.techwatch.techwatchbackend.devices.application.commandservices;

import com.techwatch.techwatchbackend.devices.domain.model.commands.StartSimulationSessionCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

/**
 * Application service contract for commands over the {@code SimulationSession} aggregate.
 */
public interface SimulationSessionCommandService {
    /**
     * Handles starting a new simulation session.
     *
     * @param command command containing the user id and the property id
     * @return created session identifier or an application error
     * @see StartSimulationSessionCommand
     */
    Result<Long, ApplicationError> handle(StartSimulationSessionCommand command);
}
