package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SessionStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.PropertyIdPersistenceConverter;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for simulation sessions.
 */
@Entity
@Table(name = "simulation_sessions")
@Getter
@Setter
@NoArgsConstructor
public class SimulationSessionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Convert(converter = PropertyIdPersistenceConverter.class)
    @Column(name = "property_id", nullable = false)
    private PropertyId propertyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceActionPersistenceEntity> actions = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsageDataRecordPersistenceEntity> usageData = new ArrayList<>();
}
