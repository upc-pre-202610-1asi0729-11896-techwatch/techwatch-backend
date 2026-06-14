package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.PropertyResource;

/**
 * Assembler to convert a Property aggregate to a PropertyResource.
 */
public class PropertyResourceFromEntityAssembler {
    /**
     * Converts a Property aggregate to a PropertyResource.
     *
     * @param entity The {@link Property} aggregate to convert.
     * @return The {@link PropertyResource} resource that results from the conversion.
     */
    public static PropertyResource toResourceFromEntity(Property entity) {
        var spaces = entity.getSpaces().stream()
                .map(SpaceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new PropertyResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getName(),
                entity.getAddress(),
                entity.getType().name(),
                spaces);
    }
}
