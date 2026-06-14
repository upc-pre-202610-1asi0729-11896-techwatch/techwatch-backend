package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.MetricType;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.DeviceIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.PropertyIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.ConsumptionPeriodPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.EnergyConsumptionPersistenceEmbeddable;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for consumption metrics.
 */
@Entity
@Table(name = "metrics")
@Getter
@Setter
@NoArgsConstructor
public class ConsumptionMetricPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PropertyIdPersistenceConverter.class)
    @Column(name = "property_id", nullable = false)
    private PropertyId propertyId;

    @Convert(converter = DeviceIdPersistenceConverter.class)
    @Column(name = "device_id")
    private DeviceId deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric_type", nullable = false)
    private MetricType metricType;

    @Embedded
    private EnergyConsumptionPersistenceEmbeddable value;

    @Embedded
    private ConsumptionPeriodPersistenceEmbeddable period;

    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;
}
