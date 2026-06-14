package com.techwatch.techwatchbackend.devices.domain.model.commands;

/**
 * Command to record an action executed against a device during a simulation session.
 *
 * @param sessionId The id of the (active) simulation session. Cannot be null or less than 1.
 * @param deviceId The id of the device the action is executed against. Cannot be null or less than 1.
 * @param actionType The type of action (e.g. TURN_ON, SET_TEMPERATURE). Cannot be null or blank.
 * @param parameterName The action parameter name, if any. Optional.
 * @param parameterValue The action parameter value, if any. Optional.
 * @param durationMinutes The duration of the action in minutes, used to compute the consumption.
 *                        Cannot be null or less than or equal to 0.
 */
public record RecordDeviceActionCommand(
        Long sessionId,
        Long deviceId,
        String actionType,
        String parameterName,
        String parameterValue,
        Double durationMinutes) {
    /**
     * Compact constructor for RecordDeviceActionCommand.
     * Validates the input data.
     * @throws IllegalArgumentException if any required field is invalid.
     */
    public RecordDeviceActionCommand {
        if (sessionId == null || sessionId < 1) {
            throw new IllegalArgumentException("sessionId cannot be null or less than 1");
        }
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("deviceId cannot be null or less than 1");
        }
        if (actionType == null || actionType.isBlank()) {
            throw new IllegalArgumentException("actionType cannot be null or blank");
        }
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new IllegalArgumentException("durationMinutes cannot be null or less than or equal to 0");
        }
    }
}
