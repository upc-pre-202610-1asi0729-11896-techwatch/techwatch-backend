package com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.adapters;

import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.PropertyRepository;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.assemblers.PropertyPersistenceAssembler;
import com.techwatch.techwatchbackend.devices.infrastructure.persistence.jpa.repositories.PropertyPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the property domain repository port with Spring Data JPA.
 */
@Repository
public class PropertyRepositoryImpl implements PropertyRepository {

    private final PropertyPersistenceRepository propertyPersistenceRepository;

    public PropertyRepositoryImpl(PropertyPersistenceRepository propertyPersistenceRepository) {
        this.propertyPersistenceRepository = propertyPersistenceRepository;
    }

    @Override
    public Optional<Property> findById(Long id) {
        return propertyPersistenceRepository.findById(id).map(PropertyPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Property> findBySpaceId(SpaceId spaceId) {
        return propertyPersistenceRepository.findBySpacesId(spaceId.spaceId())
                .map(PropertyPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Property> findAll() {
        return propertyPersistenceRepository.findAll().stream().map(PropertyPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<Property> findAllByUserId(UserId userId) {
        return propertyPersistenceRepository.findAllByUserId(userId).stream().map(PropertyPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Property save(Property property) {
        var saved = propertyPersistenceRepository.save(PropertyPersistenceAssembler.toPersistenceFromDomain(property));
        return PropertyPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsById(Long id) {
        return propertyPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByUserIdAndName(UserId userId, String name) {
        return propertyPersistenceRepository.existsByUserIdAndName(userId, name);
    }

    @Override
    public void deleteById(Long id) {
        propertyPersistenceRepository.deleteById(id);
    }
}
