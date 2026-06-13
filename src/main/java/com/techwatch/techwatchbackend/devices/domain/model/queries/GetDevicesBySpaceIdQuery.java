package com.techwatch.techwatchbackend.devices.domain.model.queries;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;

/**
 * Query to retrieve all the devices that belong to a given space.
 *
 * @param spaceId The id of the space. Cannot be null.
 */
public record GetDevicesBySpaceIdQuery(SpaceId spaceId) {
    /**
     * Compact constructor for GetDevicesBySpaceIdQuery.
     * @throws IllegalArgumentException if the spaceId is null.
     */
    public GetDevicesBySpaceIdQuery {
        if (spaceId == null) {
            throw new IllegalArgumentException("spaceId cannot be null");
        }
    }
}
