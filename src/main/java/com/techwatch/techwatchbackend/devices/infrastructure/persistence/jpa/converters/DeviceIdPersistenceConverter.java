package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts device ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class DeviceIdPersistenceConverter implements AttributeConverter<DeviceId, Long> {

    @Override
    public Long convertToDatabaseColumn(DeviceId attribute) {
        return attribute == null ? null : attribute.deviceId();
    }

    @Override
    public DeviceId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new DeviceId(dbData);
    }
}
