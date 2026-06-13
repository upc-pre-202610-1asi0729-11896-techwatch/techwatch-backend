package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.DevicePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for device persistence entities.
 */
@Repository
public interface DevicePersistenceRepository extends JpaRepository<DevicePersistenceEntity, Long> {
    List<DevicePersistenceEntity> findAllBySpaceId(SpaceId spaceId);
    boolean existsBySpaceIdAndName(SpaceId spaceId, String name);
}
