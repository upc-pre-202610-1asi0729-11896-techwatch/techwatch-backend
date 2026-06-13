package com.techwatch.techwatchbackend.devices.domain.model.entities;

import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.DeviceId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * UsageDataRecord domain entity.
 *
 * <p>
 * Represents a unit of usage/consumption data generated for a device during a simulation session.
 * It is an internal entity of the {@code SimulationSession} aggregate and has no repository of its own.
 * </p>
 */
@Getter
public class UsageDataRecord {
    /**
     * The unique identifier for the usage data record.
     */
    @Setter
    private Long id;

    /**
     * The id of the device the usage data belongs to.
     */
    @Setter
    private DeviceId deviceId;

    /**
     * The consumption value.
     */
    @Setter
    private Double consumptionValue;

    /**
     * The unit of the consumption value (e.g. Wh, kWh).
     */
    @Setter
    private String unit;

    /**
     * The moment the usage data was recorded.
     */
    @Setter
    private LocalDateTime recordedAt;

    /**
     * Constructor for UsageDataRecord.
     * @param deviceId The id of the device.
     * @param consumptionValue The consumption value.
     * @param unit The unit of the consumption value.
     */
    public UsageDataRecord(DeviceId deviceId, Double consumptionValue, String unit) {
        this.deviceId = deviceId;
        this.consumptionValue = consumptionValue;
        this.unit = unit;
        this.recordedAt = LocalDateTime.now();
    }

    /**
     * Default constructor for UsageDataRecord.
     */
    public UsageDataRecord() {
    }
}
