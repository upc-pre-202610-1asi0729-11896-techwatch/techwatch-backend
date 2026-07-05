package com.techwatch.techwatchbackend.devices.domain.model.events;

/**
 * Domain event published when a device's power status changes.
 *
 * @param deviceId The id of the device.
 * @param previousStatus The status before the change.
 * @param newStatus The status after the change.
 */
public record DeviceStatusChangedEvent(Long deviceId, String previousStatus, String newStatus) {
}
