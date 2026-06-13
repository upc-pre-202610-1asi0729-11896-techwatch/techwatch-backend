package com.techwatch.techwatchbackend.devices.application.internal.commandservices;

import com.techwatch.techwatchbackend.devices.application.commandservices.PropertyCommandService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Property;
import com.techwatch.techwatchbackend.devices.domain.model.commands.CreatePropertyCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.devices.domain.repositories.PropertyRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes property commands.
 */
@Service
public class PropertyCommandServiceImpl implements PropertyCommandService {
    private final PropertyRepository propertyRepository;

    public PropertyCommandServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreatePropertyCommand command) {
        if (propertyRepository.existsByUserIdAndName(new UserId(command.userId()), command.name()))
            return Result.failure(ApplicationError.conflict("Property",
                    "Property with name '%s' already exists for this user".formatted(command.name())));
        var property = new Property(command);
        try {
            property = propertyRepository.save(property);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-property", e.getMessage()));
        }
        return Result.success(property.getId());
    }
}
