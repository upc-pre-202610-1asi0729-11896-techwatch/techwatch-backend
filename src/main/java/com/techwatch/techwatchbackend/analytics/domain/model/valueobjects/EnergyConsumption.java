package com.techwatch.techwatchbackend.analytics.domain.model.valueobjects;

/**
 * Value object representing an amount of energy consumption with its unit.
 *
 * @param value The consumption value. It cannot be null or negative.
 * @param unit The unit of the consumption value (e.g. Wh, kWh). It cannot be null or blank.
 */
public record EnergyConsumption(Double value, String unit) {
    /**
     * Compact constructor for EnergyConsumption.
     * @throws IllegalArgumentException if the value is null/negative or the unit is null/blank.
     */
    public EnergyConsumption {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Energy consumption value cannot be null or negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Energy consumption unit cannot be null or blank");
        }
    }
}
