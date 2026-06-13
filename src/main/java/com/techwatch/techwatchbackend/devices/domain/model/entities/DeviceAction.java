package com.techwatch.techwatchbackend.devices.domain.model.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DeviceAction domain entity.
 *
 * <p>
 * Represents a single action executed against a device during a simulation session
 * (e.g. turning it on, setting a temperature). It is an internal entity of the
 * {@code SimulationSession} aggregate and therefore has no repository of its own.
 * </p>
 */
@Getter
public class DeviceAction {
    /**
     * The unique identifier for the device action.
     */
    @Setter
    private Long id;

    /**
     * The id of the device the action was executed against.
     */
    @Setter
    private DeviceId deviceId;

    /**
     * The type of action (e.g. TURN_ON, TURN_OFF, SET_TEMPERATURE).
     */
    @Setter
    private String actionType;

    /**
     * The name of the action parameter, if any (e.g. temperature).
     */
    @Setter
    private String parameterName;

    /**
     * The value of the action parameter, if any (e.g. 22).
     */
    @Setter
    private String parameterValue;

    /**
     * The moment the action was executed.
     */
    @Setter
    private LocalDateTime executedAt;

    /**
     * Constructor for DeviceAction.
     * @param deviceId The id of the target device.
     * @param actionType The type of action.
     * @param parameterName The parameter name, if any.
     * @param parameterValue The parameter value, if any.
     */
    public DeviceAction(DeviceId deviceId, String actionType, String parameterName, String parameterValue) {
        this.deviceId = deviceId;
        this.actionType = actionType;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.executedAt = LocalDateTime.now();
    }

    /**
     * Default constructor for DeviceAction.
     */
    public DeviceAction() {
    }
}
