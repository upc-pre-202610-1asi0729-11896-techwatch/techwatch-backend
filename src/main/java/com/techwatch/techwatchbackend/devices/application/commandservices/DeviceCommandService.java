package com.techwatch.techwatchbackend.devices.application.commandservices;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.commands.AddDeviceToSpaceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.EditDeviceCommand;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;

/**
 * Application service contract for commands over the {@code Device} aggregate.
 */
public interface DeviceCommandService {
    /**
     * Handles adding a new device to a space.
     *
     * @param command command containing the target space id and the device data
     * @return created device identifier or an application error
     * @see AddDeviceToSpaceCommand
     */
    Result<Long, ApplicationError> handle(AddDeviceToSpaceCommand command);

    /**
     * Handles editing the descriptive information of an existing device.
     *
     * @param command command containing the target device id and the new data
     * @return the updated device aggregate or an application error
     * @see EditDeviceCommand
     */
    Result<Device, ApplicationError> handle(EditDeviceCommand command);
}
