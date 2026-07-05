package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers.DevicePersistenceAssembler;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories.DevicePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the device domain repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a device is persisted, any domain events
 * registered on the aggregate (e.g. {@code DeviceStatusChangedEvent}) are dispatched via Spring's
 * {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DevicePersistenceRepository devicePersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DeviceRepositoryImpl(DevicePersistenceRepository devicePersistenceRepository,
                                ApplicationEventPublisher eventPublisher) {
        this.devicePersistenceRepository = devicePersistenceRepository;
        this.eventPublisher = eventPublisher;
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
        var result = DevicePersistenceAssembler.toDomainFromPersistence(saved);
        // Publish any domain events registered on the aggregate (e.g. turnOn/turnOff) once persisted.
        device.domainEvents().forEach(eventPublisher::publishEvent);
        device.clearDomainEvents();
        return result;
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
    public long countBySpaceIdIn(List<SpaceId> spaceIds) {
        return devicePersistenceRepository.countBySpaceIdIn(spaceIds);
    }

    @Override
    public void deleteById(Long id) {
        devicePersistenceRepository.deleteById(id);
    }
}
