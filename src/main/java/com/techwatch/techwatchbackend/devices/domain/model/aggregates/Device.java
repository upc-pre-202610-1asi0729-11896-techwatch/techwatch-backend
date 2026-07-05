package com.techwatch.techwatchbackend.devices.domain.model.aggregates;

import com.techwatch.techwatchbackend.devices.domain.model.commands.AddDeviceToSpaceCommand;
import com.techwatch.techwatchbackend.devices.domain.model.events.DeviceStatusChangedEvent;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceType;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PowerWatts;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SpaceId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * Device aggregate root.
 *
 * <p>
 * Represents a smart device installed in a space. A device is its own aggregate root because it is
 * queried and modified independently of its property; it references the owning space by {@link SpaceId}
 * rather than by composition. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code DevicePersistenceEntity}.
 * </p>
 */
@Getter
public class Device extends AbstractDomainAggregateRoot<Device> {
    /**
     * The unique identifier for the device.
     */
    @Setter
    private Long id;

    /**
     * The id of the space the device belongs to.
     */
    @Setter
    private SpaceId spaceId;

    /**
     * The name of the device.
     */
    @Setter
    private String name;

    /**
     * The brand of the device.
     */
    @Setter
    private String brand;

    /**
     * The model of the device.
     */
    @Setter
    private String model;

    /**
     * The type of the device.
     */
    @Setter
    private DeviceType type;

    /**
     * The power status of the device.
     */
    @Setter
    private DeviceStatus status;

    /**
     * The rated power consumption of the device.
     */
    @Setter
    private PowerWatts powerWatts;

    /**
     * Default constructor for Device.
     */
    public Device() {
    }

    /**
     * Constructor for Device with an AddDeviceToSpaceCommand.
     * The device starts in the OFF status.
     * @param command The {@link AddDeviceToSpaceCommand}.
     */
    public Device(AddDeviceToSpaceCommand command) {
        this.spaceId = new SpaceId(command.spaceId());
        this.name = command.name();
        this.brand = command.brand();
        this.model = command.model();
        this.type = command.type();
        this.powerWatts = new PowerWatts(command.powerWatts());
        this.status = DeviceStatus.OFF;
    }

    /**
     * Turns the device on.
     * Registers a {@link DeviceStatusChangedEvent} if the status actually changes.
     * @return the updated Device instance.
     */
    public Device turnOn() {
        changeStatusTo(DeviceStatus.ON);
        return this;
    }

    /**
     * Turns the device off.
     * Registers a {@link DeviceStatusChangedEvent} if the status actually changes.
     * @return the updated Device instance.
     */
    public Device turnOff() {
        changeStatusTo(DeviceStatus.OFF);
        return this;
    }

    private void changeStatusTo(DeviceStatus newStatus) {
        if (this.status == newStatus) return;
        var previousStatus = this.status;
        this.status = newStatus;
        registerDomainEvent(new DeviceStatusChangedEvent(this.id, previousStatus.name(), newStatus.name()));
    }

    /**
     * Updates the device information.
     * @param name The new name.
     * @param brand The new brand.
     * @param model The new model.
     * @param type The new type.
     * @param powerWatts The new rated power consumption.
     * @return the updated Device instance.
     */
    public Device updateInformation(String name, String brand, String model, DeviceType type, PowerWatts powerWatts) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.powerWatts = powerWatts;
        return this;
    }
}
