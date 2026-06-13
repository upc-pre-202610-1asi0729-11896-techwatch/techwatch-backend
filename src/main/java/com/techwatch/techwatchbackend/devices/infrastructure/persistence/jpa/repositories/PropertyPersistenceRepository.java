package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.entities.PropertyPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for property persistence entities.
 */
@Repository
public interface PropertyPersistenceRepository extends JpaRepository<PropertyPersistenceEntity, Long> {
    List<PropertyPersistenceEntity> findAllByUserId(UserId userId);
    boolean existsByUserIdAndName(UserId userId, String name);
}
