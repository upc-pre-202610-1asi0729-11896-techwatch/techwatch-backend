package com.techwatch.techwatchbackend.subscriptions.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.subscriptions.domain.model.valueobjects.SubscriptionId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SubscriptionIdPersistenceConverter implements AttributeConverter<SubscriptionId, Long> {

    @Override
    public Long convertToDatabaseColumn(SubscriptionId attribute) {
        return attribute == null ? null : attribute.subscriptionId();
    }

    @Override
    public SubscriptionId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SubscriptionId(dbData);
    }
}
