package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts device ids between the Analytics domain model and persistence column values.
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
