package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.EditDeviceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.EditDeviceResource;

/**
 * Assembler to convert an EditDeviceResource to an EditDeviceCommand.
 */
public class EditDeviceCommandFromResourceAssembler {
    /**
     * Converts an EditDeviceResource to an EditDeviceCommand.
     *
     * @param deviceId The id of the device to edit.
     * @param resource The {@link EditDeviceResource} resource to convert.
     * @return The {@link EditDeviceCommand} command that results from the conversion.
     * @throws IllegalArgumentException if the type is not a valid device type.
     */
    public static EditDeviceCommand toCommandFromResource(Long deviceId, EditDeviceResource resource) {
        DeviceType type;
        try {
            type = DeviceType.valueOf(resource.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "type must be one of: LIGHT, THERMOSTAT, CAMERA, SMART_PLUG, AIR_CONDITIONER, DOOR_LOCK");
        }
        return new EditDeviceCommand(
                deviceId,
                resource.name(),
                resource.brand(),
                resource.model(),
                type,
                resource.powerWatts());
    }
}
