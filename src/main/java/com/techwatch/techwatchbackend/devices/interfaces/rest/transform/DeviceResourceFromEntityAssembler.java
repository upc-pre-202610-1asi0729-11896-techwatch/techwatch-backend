package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.DeviceResource;

/**
 * Assembler to convert a Device aggregate to a DeviceResource.
 */
public class DeviceResourceFromEntityAssembler {
    /**
     * Converts a Device aggregate to a DeviceResource.
     *
     * @param entity The {@link Device} aggregate to convert.
     * @return The {@link DeviceResource} resource that results from the conversion.
     */
    public static DeviceResource toResourceFromEntity(Device entity) {
        return new DeviceResource(
                entity.getId(),
                entity.getSpaceId().spaceId(),
                entity.getName(),
                entity.getBrand(),
                entity.getModel(),
                entity.getType().name(),
                entity.getStatus().name(),
                entity.getPowerWatts().value());
    }
}
