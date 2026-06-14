package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.analytics.domain.model.aggregates.ConsumptionAlert;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities.ConsumptionAlertPersistenceEntity;

/**
 * Static assembler between consumption alert domain and persistence representations.
 */
public final class ConsumptionAlertPersistenceAssembler {

    private ConsumptionAlertPersistenceAssembler() {
    }

    public static ConsumptionAlert toDomainFromPersistence(ConsumptionAlertPersistenceEntity entity) {
        if (entity == null) return null;

        var alert = new ConsumptionAlert();
        alert.setId(entity.getId());
        alert.setUserId(entity.getUserId());
        alert.setPropertyId(entity.getPropertyId());
        alert.setDeviceId(entity.getDeviceId());
        alert.setSeverity(entity.getSeverity());
        alert.setMessage(entity.getMessage());
        alert.setThreshold(entity.getThreshold());
        alert.setCurrentValue(entity.getCurrentValue());
        alert.setIsRead(entity.getIsRead());
        alert.setTriggeredAt(entity.getTriggeredAt());
        return alert;
    }

    public static ConsumptionAlertPersistenceEntity toPersistenceFromDomain(ConsumptionAlert alert) {
        if (alert == null) return null;

        var entity = new ConsumptionAlertPersistenceEntity();
        // Only set ID if the alert is being updated; leave null for new ones so JPA generates it.
        if (alert.getId() != null) {
            entity.setId(alert.getId());
        }
        entity.setUserId(alert.getUserId());
        entity.setPropertyId(alert.getPropertyId());
        entity.setDeviceId(alert.getDeviceId());
        entity.setSeverity(alert.getSeverity());
        entity.setMessage(alert.getMessage());
        entity.setThreshold(alert.getThreshold());
        entity.setCurrentValue(alert.getCurrentValue());
        entity.setIsRead(alert.getIsRead());
        entity.setTriggeredAt(alert.getTriggeredAt());
        return entity;
    }
}
