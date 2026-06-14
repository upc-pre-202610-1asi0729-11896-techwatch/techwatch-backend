package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts user ids between the Analytics domain model and persistence column values.
 */
@Converter(autoApply = false)
public class UserIdPersistenceConverter implements AttributeConverter<UserId, Long> {

    @Override
    public Long convertToDatabaseColumn(UserId attribute) {
        return attribute == null ? null : attribute.userId();
    }

    @Override
    public UserId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new UserId(dbData);
    }
}
