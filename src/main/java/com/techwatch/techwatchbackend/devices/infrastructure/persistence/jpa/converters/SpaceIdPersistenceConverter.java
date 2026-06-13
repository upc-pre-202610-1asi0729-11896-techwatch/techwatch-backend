package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts space ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class SpaceIdPersistenceConverter implements AttributeConverter<SpaceId, Long> {

    @Override
    public Long convertToDatabaseColumn(SpaceId attribute) {
        return attribute == null ? null : attribute.spaceId();
    }

    @Override
    public SpaceId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SpaceId(dbData);
    }
}
