package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.CreatePropertyCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyType;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.CreatePropertyResource;

/**
 * Assembler to convert a CreatePropertyResource to a CreatePropertyCommand.
 */
public class CreatePropertyCommandFromResourceAssembler {
    /**
     * Converts a CreatePropertyResource to a CreatePropertyCommand.
     *
     * @param resource The {@link CreatePropertyResource} resource to convert.
     * @return The {@link CreatePropertyCommand} command that results from the conversion.
     * @throws IllegalArgumentException if the type is not a valid property type.
     */
    public static CreatePropertyCommand toCommandFromResource(CreatePropertyResource resource) {
        PropertyType type;
        try {
            type = PropertyType.valueOf(resource.type().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("type must be one of: HOUSE, APARTMENT");
        }
        return new CreatePropertyCommand(resource.userId(), resource.name(), resource.address(), type);
    }
}
