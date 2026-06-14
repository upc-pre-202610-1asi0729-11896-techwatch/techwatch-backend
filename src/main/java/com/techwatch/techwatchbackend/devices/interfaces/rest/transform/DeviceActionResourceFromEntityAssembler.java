package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.entities.DeviceAction;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.DeviceActionResource;

/**
 * Assembler to convert a DeviceAction entity to a DeviceActionResource.
 */
public class DeviceActionResourceFromEntityAssembler {
    /**
     * Converts a DeviceAction entity to a DeviceActionResource.
     *
     * @param entity The {@link DeviceAction} entity to convert.
     * @return The {@link DeviceActionResource} resource that results from the conversion.
     */
    public static DeviceActionResource toResourceFromEntity(DeviceAction entity) {
        return new DeviceActionResource(
                entity.getId(),
                entity.getDeviceId().deviceId(),
                entity.getActionType(),
                entity.getParameterName(),
                entity.getParameterValue(),
                entity.getExecutedAt());
    }
}
