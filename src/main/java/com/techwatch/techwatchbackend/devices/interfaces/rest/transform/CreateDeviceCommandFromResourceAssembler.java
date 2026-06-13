package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.AddDeviceToSpaceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreateDeviceResource;

/**
 * Assembler to convert a CreateDeviceResource to an AddDeviceToSpaceCommand.
 */
public class CreateDeviceCommandFromResourceAssembler {
    /**
     * Converts a CreateDeviceResource to an AddDeviceToSpaceCommand.
     *
     * @param resource The {@link CreateDeviceResource} resource to convert.
     * @return The {@link AddDeviceToSpaceCommand} command that results from the conversion.
     * @throws IllegalArgumentException if the type is not a valid device type.
     */
    public static AddDeviceToSpaceCommand toCommandFromResource(CreateDeviceResource resource) {
        DeviceType type;
        try {
            type = DeviceType.valueOf(resource.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "type must be one of: LIGHT, THERMOSTAT, CAMERA, SMART_PLUG, AIR_CONDITIONER, DOOR_LOCK");
        }
        return new AddDeviceToSpaceCommand(
                resource.spaceId(),
                resource.name(),
                resource.brand(),
                resource.model(),
                type,
                resource.powerWatts());
    }
}
