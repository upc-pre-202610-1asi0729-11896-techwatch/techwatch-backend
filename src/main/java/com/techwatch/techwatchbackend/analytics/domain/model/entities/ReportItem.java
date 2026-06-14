package com.techwatch.techwatchbackend.analytics.domain.model.entities;

import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.EnergyConsumption;
import lombok.Getter;
import lombok.Setter;

/**
 * ReportItem domain entity.
 *
 * <p>Represents a per-device line of a {@code ConsumptionReport}: the consumption attributed to a
 * device during the report period and how many times it was used. It is an internal entity of the
 * {@code ConsumptionReport} aggregate and has no repository of its own.</p>
 */
@Getter
public class ReportItem {
    /**
     * The unique identifier for the report item.
     */
    @Setter
    private Long id;

    /**
     * The id of the device this line refers to.
     */
    @Setter
    private DeviceId deviceId;

    /**
     * The display name of the device.
     */
    @Setter
    private String deviceName;

    /**
     * The consumption attributed to the device during the period.
     */
    @Setter
    private EnergyConsumption consumption;

    /**
     * How many times the device was used (number of usage records) during the period.
     */
    @Setter
    private Integer usageFrequency;

    /**
     * Constructor for ReportItem.
     * @param deviceId The device id.
     * @param deviceName The device display name.
     * @param consumption The consumption attributed to the device.
     * @param usageFrequency The usage frequency.
     */
    public ReportItem(DeviceId deviceId, String deviceName, EnergyConsumption consumption, Integer usageFrequency) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.consumption = consumption;
        this.usageFrequency = usageFrequency;
    }

    /**
     * Default constructor for ReportItem.
     */
    public ReportItem() {
    }
}
