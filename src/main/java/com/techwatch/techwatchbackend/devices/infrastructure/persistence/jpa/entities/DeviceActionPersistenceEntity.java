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
 * JPA persistence entity for device actions executed during a simulation session.
 */
@Entity
@Table(name = "device_actions")
@Getter
@Setter
@NoArgsConstructor
public class DeviceActionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_session_id", nullable = false)
    private SimulationSessionPersistenceEntity session;

    @Convert(converter = DeviceIdPersistenceConverter.class)
    @Column(name = "device_id", nullable = false)
    private DeviceId deviceId;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "parameter_value")
    private String parameterValue;

    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;
}
