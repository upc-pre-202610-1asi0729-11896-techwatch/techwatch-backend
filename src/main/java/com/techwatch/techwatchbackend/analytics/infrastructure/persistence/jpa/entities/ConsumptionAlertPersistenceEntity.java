package com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.AlertSeverity;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.DeviceIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.PropertyIdPersistenceConverter;
import com.techwatch.techwatchbackend.analytics.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for consumption alerts.
 */
@Entity
@Table(name = "consumption_alerts")
@Getter
@Setter
@NoArgsConstructor
public class ConsumptionAlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Convert(converter = PropertyIdPersistenceConverter.class)
    @Column(name = "property_id", nullable = false)
    private PropertyId propertyId;

    @Convert(converter = DeviceIdPersistenceConverter.class)
    @Column(name = "device_id", nullable = false)
    private DeviceId deviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertSeverity severity;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Double threshold;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "triggered_at", nullable = false)
    private LocalDateTime triggeredAt;
}
