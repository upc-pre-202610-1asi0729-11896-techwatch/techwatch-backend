package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.entities.DeviceAction;
import com.techwatch.techwatchbackend.devices.domain.model.entities.UsageDataRecord;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.DeviceActionPersistenceEntity;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.SimulationSessionPersistenceEntity;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.UsageDataRecordPersistenceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Static assembler between simulation session domain and persistence representations.
 */
public final class SimulationSessionPersistenceAssembler {

    private SimulationSessionPersistenceAssembler() {
    }

    public static SimulationSession toDomainFromPersistence(SimulationSessionPersistenceEntity entity) {
        if (entity == null) return null;

        var session = new SimulationSession();
        session.setId(entity.getId());
        session.setUserId(entity.getUserId());
        session.setPropertyId(entity.getPropertyId());
        session.setStatus(entity.getStatus());
        session.setStartedAt(entity.getStartedAt());
        session.setEndedAt(entity.getEndedAt());

        List<DeviceAction> actions = new ArrayList<>();
        for (var actionEntity : entity.getActions()) {
            var action = new DeviceAction(
                    actionEntity.getDeviceId(),
                    actionEntity.getActionType(),
                    actionEntity.getParameterName(),
                    actionEntity.getParameterValue());
            action.setId(actionEntity.getId());
            action.setExecutedAt(actionEntity.getExecutedAt());
            actions.add(action);
        }
        session.setActions(actions);

        List<UsageDataRecord> usageData = new ArrayList<>();
        for (var usageEntity : entity.getUsageData()) {
            var usage = new UsageDataRecord(
                    usageEntity.getDeviceId(),
                    usageEntity.getConsumptionValue(),
                    usageEntity.getUnit());
            usage.setId(usageEntity.getId());
            usage.setRecordedAt(usageEntity.getRecordedAt());
            usageData.add(usage);
        }
        session.setUsageData(usageData);

        return session;
    }

    public static SimulationSessionPersistenceEntity toPersistenceFromDomain(SimulationSession session) {
        if (session == null) return null;

        var entity = new SimulationSessionPersistenceEntity();
        // Only set ID if the session is being updated (has a non-null ID).
        // For new sessions, leave ID null to allow JPA to generate it.
        if (session.getId() != null) {
            entity.setId(session.getId());
        }
        entity.setUserId(session.getUserId());
        entity.setPropertyId(session.getPropertyId());
        entity.setStatus(session.getStatus());
        entity.setStartedAt(session.getStartedAt());
        entity.setEndedAt(session.getEndedAt());

        List<DeviceActionPersistenceEntity> actionEntities = new ArrayList<>();
        for (var action : session.getActions()) {
            var actionEntity = new DeviceActionPersistenceEntity();
            if (action.getId() != null) {
                actionEntity.setId(action.getId());
            }
            actionEntity.setSession(entity);
            actionEntity.setDeviceId(action.getDeviceId());
            actionEntity.setActionType(action.getActionType());
            actionEntity.setParameterName(action.getParameterName());
            actionEntity.setParameterValue(action.getParameterValue());
            actionEntity.setExecutedAt(action.getExecutedAt());
            actionEntities.add(actionEntity);
        }
        entity.setActions(actionEntities);

        List<UsageDataRecordPersistenceEntity> usageEntities = new ArrayList<>();
        for (var usage : session.getUsageData()) {
            var usageEntity = new UsageDataRecordPersistenceEntity();
            if (usage.getId() != null) {
                usageEntity.setId(usage.getId());
            }
            usageEntity.setSession(entity);
            usageEntity.setDeviceId(usage.getDeviceId());
            usageEntity.setConsumptionValue(usage.getConsumptionValue());
            usageEntity.setUnit(usage.getUnit());
            usageEntity.setRecordedAt(usage.getRecordedAt());
            usageEntities.add(usageEntity);
        }
        entity.setUsageData(usageEntities);

        return entity;
    }
}
