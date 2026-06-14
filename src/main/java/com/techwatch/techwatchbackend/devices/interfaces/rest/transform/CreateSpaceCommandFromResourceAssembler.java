package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.CreateSpaceCommand;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreateSpaceResource;

/**
 * Assembler to convert a CreateSpaceResource to a CreateSpaceCommand.
 */
public class CreateSpaceCommandFromResourceAssembler {
    /**
     * Converts a CreateSpaceResource to a CreateSpaceCommand.
     *
     * @param propertyId The id of the property the space belongs to.
     * @param resource The {@link CreateSpaceResource} resource to convert.
     * @return The {@link CreateSpaceCommand} command that results from the conversion.
     */
    public static CreateSpaceCommand toCommandFromResource(Long propertyId, CreateSpaceResource resource) {
        return new CreateSpaceCommand(propertyId, resource.name(), resource.description());
    }
}
