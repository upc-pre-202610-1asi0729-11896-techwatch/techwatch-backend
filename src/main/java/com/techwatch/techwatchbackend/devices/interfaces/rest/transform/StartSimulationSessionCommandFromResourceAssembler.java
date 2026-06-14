package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.commands.StartSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.StartSimulationSessionResource;

/**
 * Assembler to convert a StartSimulationSessionResource to a StartSimulationSessionCommand.
 */
public class StartSimulationSessionCommandFromResourceAssembler {
    /**
     * Converts a StartSimulationSessionResource to a StartSimulationSessionCommand.
     *
     * @param resource The {@link StartSimulationSessionResource} resource to convert.
     * @return The {@link StartSimulationSessionCommand} command that results from the conversion.
     */
    public static StartSimulationSessionCommand toCommandFromResource(StartSimulationSessionResource resource) {
        return new StartSimulationSessionCommand(resource.userId(), resource.propertyId());
    }
}
