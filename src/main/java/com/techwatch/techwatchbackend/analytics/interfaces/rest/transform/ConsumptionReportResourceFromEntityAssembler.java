package com.techwatch.techwatchbackend.analytics.interfaces.rest.transform;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ConsumptionReportResource;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.ReportItemResource;

/**
 * Assembler to convert a ConsumptionReport aggregate to a ConsumptionReportResource.
 */
public class ConsumptionReportResourceFromEntityAssembler {
    /**
     * Converts a ConsumptionReport aggregate to a ConsumptionReportResource.
     *
     * @param entity The {@link ConsumptionReport} aggregate to convert.
     * @return The {@link ConsumptionReportResource} resource that results from the conversion.
     */
    public static ConsumptionReportResource toResourceFromEntity(ConsumptionReport entity) {
        var items = entity.getItems().stream()
                .map(item -> new ReportItemResource(
                        item.getId(),
                        item.getDeviceId().deviceId(),
                        item.getDeviceName(),
                        item.getConsumption().value(),
                        item.getConsumption().unit(),
                        item.getUsageFrequency()))
                .toList();
        return new ConsumptionReportResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getPropertyId().propertyId(),
                entity.getPeriod().startDate(),
                entity.getPeriod().endDate(),
                entity.getTotalConsumption().value(),
                entity.getTotalConsumption().unit(),
                entity.getGeneratedAt(),
                items);
    }
}
