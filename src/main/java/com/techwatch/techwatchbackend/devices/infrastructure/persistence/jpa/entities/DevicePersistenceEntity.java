package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PowerWatts;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.PowerWattsPersistenceConverter;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.SpaceIdPersistenceConverter;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for devices.
 */
@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class DevicePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = SpaceIdPersistenceConverter.class)
    @Column(name = "space_id", nullable = false)
    private SpaceId spaceId;

    @Column(nullable = false)
    private String name;

    @Column
    private String brand;

    @Column
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status;

    @Convert(converter = PowerWattsPersistenceConverter.class)
    @Column(name = "power_watts", nullable = false)
    private PowerWatts powerWatts;
}
