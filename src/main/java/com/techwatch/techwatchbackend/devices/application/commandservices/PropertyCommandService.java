package com.techwatch.techwatchbackend.devices.application.commandservices;

import com.techwatch.techwatchbackend.devices.domain.model.commands.CreatePropertyCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.CreateSpaceCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

/**
 * Application service contract for commands over the {@code Property} aggregate.
 */
public interface PropertyCommandService {
    /**
     * Handles property registration.
     *
     * @param command command containing the initial property data
     * @return created property identifier or an application error
     * @see CreatePropertyCommand
     */
    Result<Long, ApplicationError> handle(CreatePropertyCommand command);

    /**
     * Handles the creation of a space within a property.
     *
     * @param command command containing the target property id and the space data
     * @return the owning property identifier or an application error
     * @see CreateSpaceCommand
     */
    Result<Long, ApplicationError> handle(CreateSpaceCommand command);
}
