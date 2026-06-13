package com.techwatch.techwatchbackend.devices.application.internal.queryservices;

import com.techwatch.techwatchbackend.devices.application.queryservices.PropertyQueryService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertiesByUserIdQuery;
import com.techwatch.techwatchbackend.devices.domain.model.queries.GetPropertyByIdQuery;
import com.techwatch.techwatchbackend.devices.domain.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves property read queries.
 */
@Service
public class PropertyQueryServiceImpl implements PropertyQueryService {
    private final PropertyRepository propertyRepository;

    public PropertyQueryServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Optional<Property> handle(GetPropertyByIdQuery query) {
        return propertyRepository.findById(query.propertyId());
    }

    @Override
    public List<Property> handle(GetPropertiesByUserIdQuery query) {
        return propertyRepository.findAllByUserId(query.userId());
    }
}
