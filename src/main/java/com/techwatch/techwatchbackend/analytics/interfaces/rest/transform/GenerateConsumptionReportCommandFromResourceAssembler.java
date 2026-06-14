package com.techwatch.techwatchbackend.analytics.interfaces.rest.transform;

import com.techwatch.techwatchbackend.analytics.domain.model.commands.GenerateConsumptionReportCommand;
import com.techwatch.techwatchbackend.analytics.interfaces.rest.resources.GenerateConsumptionReportResource;

/**
 * Assembler to convert a GenerateConsumptionReportResource to a GenerateConsumptionReportCommand.
 */
public class GenerateConsumptionReportCommandFromResourceAssembler {
    /**
     * Converts a GenerateConsumptionReportResource to a GenerateConsumptionReportCommand.
     *
     * @param resource The {@link GenerateConsumptionReportResource} resource to convert.
     * @return The {@link GenerateConsumptionReportCommand} command that results from the conversion.
     */
    public static GenerateConsumptionReportCommand toCommandFromResource(GenerateConsumptionReportResource resource) {
        return new GenerateConsumptionReportCommand(
                resource.userId(),
                resource.propertyId(),
                resource.startDate(),
                resource.endDate());
    }
}
