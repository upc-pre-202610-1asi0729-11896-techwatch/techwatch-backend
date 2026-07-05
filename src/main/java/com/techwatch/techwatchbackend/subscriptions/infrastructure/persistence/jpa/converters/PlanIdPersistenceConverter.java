package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.PlanId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PlanIdPersistenceConverter implements AttributeConverter<PlanId, Long> {

    @Override
    public Long convertToDatabaseColumn(PlanId attribute) {
        return attribute == null ? null : attribute.planId();
    }

    @Override
    public PlanId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PlanId(dbData);
    }
}
