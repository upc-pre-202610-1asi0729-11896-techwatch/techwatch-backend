package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts property ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class PropertyIdPersistenceConverter implements AttributeConverter<PropertyId, Long> {

    @Override
    public Long convertToDatabaseColumn(PropertyId attribute) {
        return attribute == null ? null : attribute.propertyId();
    }

    @Override
    public PropertyId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PropertyId(dbData);
    }
}
