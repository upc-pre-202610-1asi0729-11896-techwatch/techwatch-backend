package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.PropertyPersistenceEntity;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.SpacePersistenceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Static assembler between property domain and persistence representations.
 */
public final class PropertyPersistenceAssembler {

    private PropertyPersistenceAssembler() {
    }

    public static Property toDomainFromPersistence(PropertyPersistenceEntity entity) {
        if (entity == null) return null;

        var property = new Property();
        property.setId(entity.getId());
        property.setUserId(entity.getUserId());
        property.setName(entity.getName());
        property.setAddress(entity.getAddress());
        property.setType(entity.getType());

        List<Space> spaces = new ArrayList<>();
        for (var spaceEntity : entity.getSpaces()) {
            var space = new Space(spaceEntity.getName(), spaceEntity.getDescription());
            space.setId(spaceEntity.getId());
            spaces.add(space);
        }
        property.setSpaces(spaces);

        return property;
    }

    public static PropertyPersistenceEntity toPersistenceFromDomain(Property property) {
        if (property == null) return null;

        var entity = new PropertyPersistenceEntity();
        // Only set ID if the property is being updated (has a non-null ID).
        // For new properties, leave ID null to allow JPA to generate it.
        if (property.getId() != null) {
            entity.setId(property.getId());
        }
        entity.setUserId(property.getUserId());
        entity.setName(property.getName());
        entity.setAddress(property.getAddress());
        entity.setType(property.getType());

        List<SpacePersistenceEntity> spaceEntities = new ArrayList<>();
        for (var space : property.getSpaces()) {
            var spaceEntity = new SpacePersistenceEntity();
            // Only set ID for spaces being updated; leave null for new ones.
            if (space.getId() != null) {
                spaceEntity.setId(space.getId());
            }
            spaceEntity.setProperty(entity);
            spaceEntity.setName(space.getName());
            spaceEntity.setDescription(space.getDescription());
            spaceEntities.add(spaceEntity);
        }
        entity.setSpaces(spaceEntities);

        return entity;
    }
}
