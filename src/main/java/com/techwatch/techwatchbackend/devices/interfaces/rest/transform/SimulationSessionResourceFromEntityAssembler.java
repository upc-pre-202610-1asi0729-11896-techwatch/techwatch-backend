package com.techwatch.techwatchbackend.devices.interfaces.rest.transform;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.interfaces.rest.resources.SimulationSessionResource;

/**
 * Assembler to convert a SimulationSession aggregate to a SimulationSessionResource.
 */
public class SimulationSessionResourceFromEntityAssembler {
    /**
     * Converts a SimulationSession aggregate to a SimulationSessionResource.
     *
     * @param entity The {@link SimulationSession} aggregate to convert.
     * @return The {@link SimulationSessionResource} resource that results from the conversion.
     */
    public static SimulationSessionResource toResourceFromEntity(SimulationSession entity) {
        var actions = entity.getActions().stream()
                .map(DeviceActionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        var usageData = entity.getUsageData().stream()
                .map(UsageDataResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new SimulationSessionResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getPropertyId().propertyId(),
                entity.getStatus().name(),
                entity.getStartedAt(),
                entity.getEndedAt(),
                actions,
                usageData);
    }
}
