package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.entities.UsageDataRecord;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.UsageDataResource;

/**
 * Assembler to convert a UsageDataRecord entity to a UsageDataResource.
 */
public class UsageDataResourceFromEntityAssembler {
    /**
     * Converts a UsageDataRecord entity to a UsageDataResource.
     *
     * @param entity The {@link UsageDataRecord} entity to convert.
     * @return The {@link UsageDataResource} resource that results from the conversion.
     */
    public static UsageDataResource toResourceFromEntity(UsageDataRecord entity) {
        return new UsageDataResource(
                entity.getId(),
                entity.getDeviceId().deviceId(),
                entity.getConsumptionValue(),
                entity.getUnit(),
                entity.getRecordedAt());
    }
}
