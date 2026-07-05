package com.techwatch.techwatchbackend.devices.domain.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;

import java.util.List;
import java.util.Optional;

/**
 * Device repository port.
 */
public interface DeviceRepository {
    Optional<Device> findById(Long id);
    List<Device> findAll();
    List<Device> findAllBySpaceId(SpaceId spaceId);
    Device save(Device device);
    boolean existsById(Long id);
    boolean existsBySpaceIdAndName(SpaceId spaceId, String name);
    long countBySpaceIdIn(List<SpaceId> spaceIds);
    void deleteById(Long id);
}
