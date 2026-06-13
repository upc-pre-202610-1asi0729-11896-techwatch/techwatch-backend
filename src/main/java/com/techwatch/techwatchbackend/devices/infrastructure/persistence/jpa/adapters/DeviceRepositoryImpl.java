package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers.DevicePersistenceAssembler;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories.DevicePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the device domain repository port with Spring Data JPA.
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DevicePersistenceRepository devicePersistenceRepository;

    public DeviceRepositoryImpl(DevicePersistenceRepository devicePersistenceRepository) {
        this.devicePersistenceRepository = devicePersistenceRepository;
    }

    @Override
    public Optional<Device> findById(Long id) {
        return devicePersistenceRepository.findById(id).map(DevicePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Device> findAll() {
        return devicePersistenceRepository.findAll().stream().map(DevicePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<Device> findAllBySpaceId(SpaceId spaceId) {
        return devicePersistenceRepository.findAllBySpaceId(spaceId).stream().map(DevicePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Device save(Device device) {
        var saved = devicePersistenceRepository.save(DevicePersistenceAssembler.toPersistenceFromDomain(device));
        return DevicePersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return devicePersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsBySpaceIdAndName(SpaceId spaceId, String name) {
        return devicePersistenceRepository.existsBySpaceIdAndName(spaceId, name);
    }

    @Override
    public void deleteById(Long id) {
        devicePersistenceRepository.deleteById(id);
    }
}
