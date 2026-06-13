package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.DevicePersistenceEntity;

/**
 * Static assembler between device domain and persistence representations.
 */
public final class DevicePersistenceAssembler {

    private DevicePersistenceAssembler() {
    }

    public static Device toDomainFromPersistence(DevicePersistenceEntity entity) {
        if (entity == null) return null;

        var device = new Device();
        device.setId(entity.getId());
        device.setSpaceId(entity.getSpaceId());
        device.setName(entity.getName());
        device.setBrand(entity.getBrand());
        device.setModel(entity.getModel());
        device.setType(entity.getType());
        device.setStatus(entity.getStatus());
        device.setPowerWatts(entity.getPowerWatts());
        return device;
    }

    public static DevicePersistenceEntity toPersistenceFromDomain(Device device) {
        if (device == null) return null;

        var entity = new DevicePersistenceEntity();
        // Only set ID if the device is being updated (has a non-null ID).
        // For new devices, leave ID null to allow JPA to generate it.
        if (device.getId() != null) {
            entity.setId(device.getId());
        }
        entity.setSpaceId(device.getSpaceId());
        entity.setName(device.getName());
        entity.setBrand(device.getBrand());
        entity.setModel(device.getModel());
        entity.setType(device.getType());
        entity.setStatus(device.getStatus());
        entity.setPowerWatts(device.getPowerWatts());
        return entity;
    }
}
