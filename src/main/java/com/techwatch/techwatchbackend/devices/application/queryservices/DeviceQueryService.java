package com.techwatch.techwatchbackend.devices.application.queryservices;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDeviceByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDevicesBySpaceIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for device read queries.
 */
public interface DeviceQueryService {
    /**
     * Handles retrieval of a device by id.
     *
     * @param query device-id query
     * @return matching device, if found
     * @see GetDeviceByIdQuery
     */
    Optional<Device> handle(GetDeviceByIdQuery query);

    /**
     * Handles retrieval of all devices that belong to a space.
     *
     * @param query space-id query
     * @return list of devices in the space
     * @see GetDevicesBySpaceIdQuery
     */
    List<Device> handle(GetDevicesBySpaceIdQuery query);
}
