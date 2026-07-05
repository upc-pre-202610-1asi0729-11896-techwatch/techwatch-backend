package com.techwatch.techwatchbackend.devices.application.internal.commandservices;

import com.techwatch.techwatchbackend.devices.application.commandservices.DeviceCommandService;
import com.techwatch.techwatchbackend.devices.application.internal.outboundservices.acl.ExternalSubscriptionService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.commands.AddDeviceToSpaceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.DeleteDeviceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.commands.EditDeviceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PowerWatts;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
import com.techwatch.techwatchbackend.devices.domain.repositories.PropertyRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes device commands.
 */
@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {
    private final DeviceRepository deviceRepository;
    private final PropertyRepository propertyRepository;
    private final ExternalSubscriptionService externalSubscriptionService;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository,
                                    PropertyRepository propertyRepository,
                                    ExternalSubscriptionService externalSubscriptionService) {
        this.deviceRepository = deviceRepository;
        this.propertyRepository = propertyRepository;
        this.externalSubscriptionService = externalSubscriptionService;
    }

    @Override
    public Result<Long, ApplicationError> handle(AddDeviceToSpaceCommand command) {
        var spaceId = new SpaceId(command.spaceId());
        if (deviceRepository.existsBySpaceIdAndName(spaceId, command.name()))
            return Result.failure(ApplicationError.conflict("Device",
                    "Device with name '%s' already exists in this space".formatted(command.name())));
        // Policy: before adding a device, the system validates the device limit of the
        // user's plan (ACL towards the Subscriptions context)
        var property = propertyRepository.findBySpaceId(spaceId);
        if (property.isPresent()) {
            var userId = property.get().getUserId();
            var maxDevices = externalSubscriptionService.fetchMaxDevicesForUser(userId);
            var userSpaceIds = propertyRepository.findAllByUserId(userId).stream()
                    .flatMap(userProperty -> userProperty.getSpaces().stream())
                    .map(Space::getId)
                    .map(SpaceId::new)
                    .toList();
            var currentDevices = deviceRepository.countBySpaceIdIn(userSpaceIds);
            if (currentDevices >= maxDevices)
                return Result.failure(ApplicationError.businessRuleViolation("add-device-to-space",
                        "The user's plan allows a maximum of %d devices".formatted(maxDevices)));
        }
        var device = new Device(command);
        try {
            device = deviceRepository.save(device);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("add-device-to-space", e.getMessage()));
        }
        return Result.success(device.getId());
    }

    @Override
    public Result<Device, ApplicationError> handle(EditDeviceCommand command) {
        var result = deviceRepository.findById(command.deviceId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("Device", command.deviceId().toString()));
        var device = result.get();
        boolean nameTaken = deviceRepository.findAllBySpaceId(device.getSpaceId()).stream()
                .anyMatch(other -> !other.getId().equals(device.getId()) && other.getName().equals(command.name()));
        if (nameTaken)
            return Result.failure(ApplicationError.conflict("Device",
                    "Device with name '%s' already exists in this space".formatted(command.name())));
        try {
            var updated = deviceRepository.save(device.updateInformation(
                    command.name(), command.brand(), command.model(), command.type(), new PowerWatts(command.powerWatts())));
            return Result.success(updated);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("edit-device", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeleteDeviceCommand command) {
        if (!deviceRepository.existsById(command.deviceId()))
            return Result.failure(ApplicationError.notFound("Device", command.deviceId().toString()));
        try {
            deviceRepository.deleteById(command.deviceId());
            return Result.success(command.deviceId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("delete-device", e.getMessage()));
        }
    }
}
