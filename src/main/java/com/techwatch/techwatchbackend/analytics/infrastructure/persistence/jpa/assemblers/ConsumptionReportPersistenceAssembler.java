package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionReport;
import com.techwatch.techwatchbackend.analytics.domain.model.entities.ReportItem;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.ConsumptionPeriod;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.EnergyConsumption;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.ConsumptionPeriodPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.EnergyConsumptionPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionReportPersistenceEntity;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ReportItemPersistenceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Static assembler between consumption report domain and persistence representations.
 */
public final class ConsumptionReportPersistenceAssembler {

    private ConsumptionReportPersistenceAssembler() {
    }

    public static ConsumptionReport toDomainFromPersistence(ConsumptionReportPersistenceEntity entity) {
        if (entity == null) return null;

        var report = new ConsumptionReport();
        report.setId(entity.getId());
        report.setUserId(entity.getUserId());
        report.setPropertyId(entity.getPropertyId());
        report.setPeriod(toDomainFromPersistence(entity.getPeriod()));
        report.setTotalConsumption(toDomainFromPersistence(entity.getTotalConsumption()));
        report.setGeneratedAt(entity.getGeneratedAt());

        List<ReportItem> items = new ArrayList<>();
        for (var itemEntity : entity.getItems()) {
            var item = new ReportItem(
                    itemEntity.getDeviceId(),
                    itemEntity.getDeviceName(),
                    toDomainFromPersistence(itemEntity.getConsumption()),
                    itemEntity.getUsageFrequency());
            item.setId(itemEntity.getId());
            items.add(item);
        }
        report.setItems(items);

        return report;
    }

    public static ConsumptionReportPersistenceEntity toPersistenceFromDomain(ConsumptionReport report) {
        if (report == null) return null;

        var entity = new ConsumptionReportPersistenceEntity();
        // Only set ID if the report is being updated; leave null for new ones so JPA generates it.
        if (report.getId() != null) {
            entity.setId(report.getId());
        }
        entity.setUserId(report.getUserId());
        entity.setPropertyId(report.getPropertyId());
        entity.setPeriod(toPersistenceFromDomain(report.getPeriod()));
        entity.setTotalConsumption(toPersistenceFromDomain(report.getTotalConsumption()));
        entity.setGeneratedAt(report.getGeneratedAt());

        List<ReportItemPersistenceEntity> itemEntities = new ArrayList<>();
        for (var item : report.getItems()) {
            var itemEntity = new ReportItemPersistenceEntity();
            if (item.getId() != null) {
                itemEntity.setId(item.getId());
            }
            itemEntity.setReport(entity);
            itemEntity.setDeviceId(item.getDeviceId());
            itemEntity.setDeviceName(item.getDeviceName());
            itemEntity.setConsumption(toPersistenceFromDomain(item.getConsumption()));
            itemEntity.setUsageFrequency(item.getUsageFrequency());
            itemEntities.add(itemEntity);
        }
        entity.setItems(itemEntities);

        return entity;
    }

    private static EnergyConsumption toDomainFromPersistence(EnergyConsumptionPersistenceEmbeddable value) {
        return value == null ? null : new EnergyConsumption(value.getValue(), value.getUnit());
    }

    private static ConsumptionPeriod toDomainFromPersistence(ConsumptionPeriodPersistenceEmbeddable period) {
        return period == null ? null : new ConsumptionPeriod(period.getStartDate(), period.getEndDate());
    }

    private static EnergyConsumptionPersistenceEmbeddable toPersistenceFromDomain(EnergyConsumption value) {
        return value == null ? null : new EnergyConsumptionPersistenceEmbeddable(value.value(), value.unit());
    }

    private static ConsumptionPeriodPersistenceEmbeddable toPersistenceFromDomain(ConsumptionPeriod period) {
        return period == null ? null : new ConsumptionPeriodPersistenceEmbeddable(period.startDate(), period.endDate());
    }
}
