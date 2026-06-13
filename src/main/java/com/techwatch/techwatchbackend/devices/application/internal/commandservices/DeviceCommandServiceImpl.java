package com.techwatch.techwatchbackend.devices.application.internal.commandservices;

import com.techwatch.techwatchbackend.devices.application.commandservices.DeviceCommandService;
import com.techwatch.techwatchbackend.devices.domain.model.aggregates.Device;
import com.techwatch.techwatchbackend.devices.domain.model.commands.AddDeviceToSpaceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.devices.domain.repositories.DeviceRepository;
import com.techwatch.techwatchbackend.shared.application.result.ApplicationError;
import com.techwatch.techwatchbackend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes device commands.
 */
@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {
    private final DeviceRepository deviceRepository;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(AddDeviceToSpaceCommand command) {
        // TODO (Subscriptions): antes de crear el device se debe validar el límite de dispositivos
        // del plan del usuario vía ACL hacia el contexto Subscriptions
        // (SubscriptionsContextFacade.fetchMaxDevicesForUser, resolviendo el userId por
        // device -> space -> property -> userId). Pospuesto hasta implementar Subscriptions.
        var spaceId = new SpaceId(command.spaceId());
        if (deviceRepository.existsBySpaceIdAndName(spaceId, command.name()))
            return Result.failure(ApplicationError.conflict("Device",
                    "Device with name '%s' already exists in this space".formatted(command.name())));
        var device = new Device(command);
        try {
            device = deviceRepository.save(device);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("add-device-to-space", e.getMessage()));
        }
        return Result.success(device.getId());
    }
}
