package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.DeviceIdPersistenceConverter;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for usage data records generated during a simulation session.
 */
@Entity
@Table(name = "usage_data")
@Getter
@Setter
@NoArgsConstructor
public class UsageDataRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_session_id", nullable = false)
    private SimulationSessionPersistenceEntity session;

    @Convert(converter = DeviceIdPersistenceConverter.class)
    @Column(name = "device_id", nullable = false)
    private DeviceId deviceId;

    @Column(name = "consumption_value", nullable = false)
    private Double consumptionValue;

    @Column(nullable = false)
    private String unit;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}
