package com.techwatch.techwatchbackend.devices.application.commandservices;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.commands.EndSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.RecordDeviceActionCommand;
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

    /**
     * Handles recording an action against a device during an active simulation session.
     *
     * @param command command containing the session id, device id, action data and duration
     * @return the updated session aggregate or an application error
     * @see RecordDeviceActionCommand
     */
    Result<SimulationSession, ApplicationError> handle(RecordDeviceActionCommand command);

    /**
     * Handles ending an active simulation session.
     *
     * @param command command containing the target session id
     * @return the ended session aggregate or an application error
     * @see EndSimulationSessionCommand
     */
    Result<SimulationSession, ApplicationError> handle(EndSimulationSessionCommand command);
}
