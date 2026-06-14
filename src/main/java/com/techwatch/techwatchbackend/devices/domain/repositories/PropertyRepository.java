package com.techwatch.techwatchbackend.devices.domain.repositories;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Property repository port.
 */
public interface PropertyRepository {
    Optional<Property> findById(Long id);
    List<Property> findAll();
    List<Property> findAllByUserId(UserId userId);
    Property save(Property property);
    boolean existsById(Long id);
    boolean existsByUserIdAndName(UserId userId, String name);
    void deleteById(Long id);
}
