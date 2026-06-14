package com.techwatch.techwatchbackend.analytics.interfaces.rest.transform;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionMetricResource;

/**
 * Assembler to convert a ConsumptionMetric aggregate to a ConsumptionMetricResource.
 */
public class ConsumptionMetricResourceFromEntityAssembler {
    /**
     * Converts a ConsumptionMetric aggregate to a ConsumptionMetricResource.
     *
     * @param entity The {@link ConsumptionMetric} aggregate to convert.
     * @return The {@link ConsumptionMetricResource} resource that results from the conversion.
     */
    public static ConsumptionMetricResource toResourceFromEntity(ConsumptionMetric entity) {
        Long deviceId = entity.getDeviceId() == null ? null : entity.getDeviceId().deviceId();
        return new ConsumptionMetricResource(
                entity.getId(),
                entity.getPropertyId().propertyId(),
                deviceId,
                entity.getMetricType().name(),
                entity.getValue().value(),
                entity.getValue().unit(),
                entity.getPeriod().startDate(),
                entity.getPeriod().endDate(),
                entity.getCalculatedAt());
    }
}
