package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyType;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.techwatch.techwatchbackend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for properties.
 */
@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
public class PropertyPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType type;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpacePersistenceEntity> spaces = new ArrayList<>();
}
