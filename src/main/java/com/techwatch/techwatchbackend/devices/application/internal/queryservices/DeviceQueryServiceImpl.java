package com.techwatch.techwatchbackend.devices.application.internal.queryservices;

import com.techwatch.techwatchbackend.devices.application.queryservices.DeviceQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDeviceByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetDevicesBySpaceIdQuery;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves device read queries.
 */
@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {
    private final DeviceRepository deviceRepository;

    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Optional<Device> handle(GetDeviceByIdQuery query) {
        return deviceRepository.findById(query.deviceId());
    }

    @Override
    public List<Device> handle(GetDevicesBySpaceIdQuery query) {
        return deviceRepository.findAllBySpaceId(query.spaceId());
    }
}
