package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.DeviceIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.EnergyConsumptionPersistenceEmbeddable;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for report items.
 */
@Entity
@Table(name = "report_items")
@Getter
@Setter
@NoArgsConstructor
public class ReportItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_report_id", nullable = false)
    private ConsumptionReportPersistenceEntity report;

    @Convert(converter = DeviceIdPersistenceConverter.class)
    @Column(name = "device_id", nullable = false)
    private DeviceId deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "consumption_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "consumption_unit"))
    })
    private EnergyConsumptionPersistenceEmbeddable consumption;

    @Column(name = "usage_frequency", nullable = false)
    private Integer usageFrequency;
}
