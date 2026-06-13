package com.techwatch.techwatchbackend.devices.application.queryservices;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.SimulationSession;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetActiveSimulationSessionByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetSimulationSessionByIdQuery;

import java.util.Optional;

/**
 * Application service contract for simulation session read queries.
 */
public interface SimulationSessionQueryService {
    /**
     * Handles retrieval of a simulation session by id.
     *
     * @param query session-id query
     * @return matching session, if found
     * @see GetSimulationSessionByIdQuery
     */
    Optional<SimulationSession> handle(GetSimulationSessionByIdQuery query);

    /**
     * Handles retrieval of the active simulation session of a user.
     *
     * @param query user-id query
     * @return the active session, if any
     * @see GetActiveSimulationSessionByUserIdQuery
     */
    Optional<SimulationSession> handle(GetActiveSimulationSessionByUserIdQuery query);
}
