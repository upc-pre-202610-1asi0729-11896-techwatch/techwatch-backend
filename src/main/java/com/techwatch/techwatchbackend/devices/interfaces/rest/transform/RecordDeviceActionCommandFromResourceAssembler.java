package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.RecordDeviceActionCommand;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.RecordDeviceActionResource;

/**
 * Assembler to convert a RecordDeviceActionResource to a RecordDeviceActionCommand.
 */
public class RecordDeviceActionCommandFromResourceAssembler {
    /**
     * Converts a RecordDeviceActionResource to a RecordDeviceActionCommand.
     *
     * @param sessionId The id of the simulation session.
     * @param resource The {@link RecordDeviceActionResource} resource to convert.
     * @return The {@link RecordDeviceActionCommand} command that results from the conversion.
     */
    public static RecordDeviceActionCommand toCommandFromResource(Long sessionId, RecordDeviceActionResource resource) {
        return new RecordDeviceActionCommand(
                sessionId,
                resource.deviceId(),
                resource.actionType(),
                resource.parameterName(),
                resource.parameterValue(),
                resource.durationMinutes());
    }
}
