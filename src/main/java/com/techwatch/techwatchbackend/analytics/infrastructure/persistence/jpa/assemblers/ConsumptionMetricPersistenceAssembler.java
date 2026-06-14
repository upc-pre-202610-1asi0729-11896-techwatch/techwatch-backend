package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionMetric;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.ConsumptionPeriod;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.EnergyConsumption;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.ConsumptionPeriodPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.EnergyConsumptionPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionMetricPersistenceEntity;

/**
 * Static assembler between consumption metric domain and persistence representations.
 */
public final class ConsumptionMetricPersistenceAssembler {

    private ConsumptionMetricPersistenceAssembler() {
    }

    public static ConsumptionMetric toDomainFromPersistence(ConsumptionMetricPersistenceEntity entity) {
        if (entity == null) return null;

        var metric = new ConsumptionMetric();
        metric.setId(entity.getId());
        metric.setPropertyId(entity.getPropertyId());
        metric.setDeviceId(entity.getDeviceId());
        metric.setMetricType(entity.getMetricType());
        metric.setValue(toDomainFromPersistence(entity.getValue()));
        metric.setPeriod(toDomainFromPersistence(entity.getPeriod()));
        metric.setCalculatedAt(entity.getCalculatedAt());
        return metric;
    }

    public static ConsumptionMetricPersistenceEntity toPersistenceFromDomain(ConsumptionMetric metric) {
        if (metric == null) return null;

        var entity = new ConsumptionMetricPersistenceEntity();
        // Only set ID if the metric is being updated; leave null for new ones so JPA generates it.
        if (metric.getId() != null) {
            entity.setId(metric.getId());
        }
        entity.setPropertyId(metric.getPropertyId());
        entity.setDeviceId(metric.getDeviceId());
        entity.setMetricType(metric.getMetricType());
        entity.setValue(toPersistenceFromDomain(metric.getValue()));
        entity.setPeriod(toPersistenceFromDomain(metric.getPeriod()));
        entity.setCalculatedAt(metric.getCalculatedAt());
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
