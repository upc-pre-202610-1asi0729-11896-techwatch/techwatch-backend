package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for an energy consumption value object.
 */
@Embeddable
public class EnergyConsumptionPersistenceEmbeddable {

    @Column(name = "value")
    private Double value;

    @Column(name = "unit")
    private String unit;

    public EnergyConsumptionPersistenceEmbeddable() {
    }

    public EnergyConsumptionPersistenceEmbeddable(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
