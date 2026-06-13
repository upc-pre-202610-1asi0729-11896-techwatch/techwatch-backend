package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PowerWatts;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts power-watts values between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class PowerWattsPersistenceConverter implements AttributeConverter<PowerWatts, Double> {

    @Override
    public Double convertToDatabaseColumn(PowerWatts attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public PowerWatts convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new PowerWatts(dbData);
    }
}
