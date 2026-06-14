package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.SpaceResource;

/**
 * Assembler to convert a Space entity to a SpaceResource.
 */
public class SpaceResourceFromEntityAssembler {
    /**
     * Converts a Space entity to a SpaceResource.
     *
     * @param entity The {@link Space} entity to convert.
     * @return The {@link SpaceResource} resource that results from the conversion.
     */
    public static SpaceResource toResourceFromEntity(Space entity) {
        return new SpaceResource(entity.getId(), entity.getName(), entity.getDescription());
    }
}
