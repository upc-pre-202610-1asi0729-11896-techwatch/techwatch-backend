package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.PropertyIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.ConsumptionPeriodPersistenceEmbeddable;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.embeddables.EnergyConsumptionPersistenceEmbeddable;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for consumption reports.
 */
@Entity
@Table(name = "consumption_reports")
@Getter
@Setter
@NoArgsConstructor
public class ConsumptionReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Convert(converter = PropertyIdPersistenceConverter.class)
    @Column(name = "property_id", nullable = false)
    private PropertyId propertyId;

    @Embedded
    private ConsumptionPeriodPersistenceEmbeddable period;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "total_consumption")),
            @AttributeOverride(name = "unit", column = @Column(name = "total_consumption_unit"))
    })
    private EnergyConsumptionPersistenceEmbeddable totalConsumption;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportItemPersistenceEntity> items = new ArrayList<>();
}
