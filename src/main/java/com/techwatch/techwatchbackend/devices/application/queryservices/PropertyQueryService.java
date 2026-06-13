package com.techwatch.techwatchbackend.devices.application.queryservices;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertiesByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertyByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetSpaceByPropertyIdAndNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for property read queries.
 */
public interface PropertyQueryService {
    /**
     * Handles retrieval of a property by id.
     *
     * @param query property-id query
     * @return matching property, if found
     * @see GetPropertyByIdQuery
     */
    Optional<Property> handle(GetPropertyByIdQuery query);

    /**
     * Handles retrieval of all properties owned by a user.
     *
     * @param query user-id query
     * @return list of properties owned by the user
     * @see GetPropertiesByUserIdQuery
     */
    List<Property> handle(GetPropertiesByUserIdQuery query);

    /**
     * Handles retrieval of a space by its owning property and name.
     *
     * @param query property-id and space-name query
     * @return matching space, if found
     * @see GetSpaceByPropertyIdAndNameQuery
     */
    Optional<Space> handle(GetSpaceByPropertyIdAndNameQuery query);
}
