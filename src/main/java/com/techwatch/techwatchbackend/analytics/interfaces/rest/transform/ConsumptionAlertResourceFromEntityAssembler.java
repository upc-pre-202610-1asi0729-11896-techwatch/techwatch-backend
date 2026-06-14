package com.techwatch.techwatchbackend.analytics.interfaces.rest.transform;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionAlertResource;

/**
 * Assembler to convert a ConsumptionAlert aggregate to a ConsumptionAlertResource.
 */
public class ConsumptionAlertResourceFromEntityAssembler {
    /**
     * Converts a ConsumptionAlert aggregate to a ConsumptionAlertResource.
     *
     * @param entity The {@link ConsumptionAlert} aggregate to convert.
     * @return The {@link ConsumptionAlertResource} resource that results from the conversion.
     */
    public static ConsumptionAlertResource toResourceFromEntity(ConsumptionAlert entity) {
        return new ConsumptionAlertResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getPropertyId().propertyId(),
                entity.getDeviceId().deviceId(),
                entity.getSeverity().name(),
                entity.getMessage(),
                entity.getThreshold(),
                entity.getCurrentValue(),
                entity.getIsRead(),
                entity.getTriggeredAt());
    }
}
