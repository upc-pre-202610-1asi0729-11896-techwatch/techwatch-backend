package com.techwatch.techwatchbackend.analytics.domain.model.aggregates;

import com.techwatch.techwatchbackend.analytics.domain.model.commands.GenerateConsumptionReportCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.entities.ReportItem;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.ConsumptionPeriod;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.EnergyConsumption;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ConsumptionReport aggregate root.
 *
 * <p>Represents a consumption report generated for a property over a period, broken down per device
 * into {@link ReportItem}s. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code ConsumptionReportPersistenceEntity}.</p>
 */
@Getter
public class ConsumptionReport extends AbstractDomainAggregateRoot<ConsumptionReport> {
    private static final String DEFAULT_UNIT = "Wh";

    /**
     * The unique identifier for the report.
     */
    @Setter
    private Long id;

    /**
     * The id of the user the report is for.
     */
    @Setter
    private UserId userId;

    /**
     * The id of the property the report covers.
     */
    @Setter
    private PropertyId propertyId;

    /**
     * The period the report covers.
     */
    @Setter
    private ConsumptionPeriod period;

    /**
     * The total consumption over the period.
     */
    @Setter
    private EnergyConsumption totalConsumption;

    /**
     * The moment the report was generated.
     */
    @Setter
    private LocalDateTime generatedAt;

    /**
     * Whether the report was generated automatically by the system (e.g. when a simulation session
     * ends) rather than requested on-demand by the user.
     */
    @Setter
    private Boolean isAutomatic;

    /**
     * The per-device lines of the report.
     */
    private List<ReportItem> items;

    /**
     * Default constructor for ConsumptionReport.
     * Initializes an empty list of items and a zero total consumption.
     */
    public ConsumptionReport() {
        this.items = new ArrayList<>();
        this.totalConsumption = new EnergyConsumption(0.0, DEFAULT_UNIT);
    }

    /**
     * Constructor for ConsumptionReport with a GenerateConsumptionReportCommand.
     * @param command The {@link GenerateConsumptionReportCommand}.
     */
    public ConsumptionReport(GenerateConsumptionReportCommand command) {
        this.userId = new UserId(command.userId());
        this.propertyId = new PropertyId(command.propertyId());
        this.period = new ConsumptionPeriod(command.startDate(), command.endDate());
        this.generatedAt = LocalDateTime.now();
        this.isAutomatic = command.isAutomatic();
        this.items = new ArrayList<>();
        this.totalConsumption = new EnergyConsumption(0.0, DEFAULT_UNIT);
    }

    /**
     * Adds a per-device line to the report and accumulates its consumption into the total.
     * @param deviceId The device id.
     * @param deviceName The device display name.
     * @param consumption The consumption attributed to the device.
     * @param usageFrequency The usage frequency.
     * @return the created {@link ReportItem}.
     */
    public ReportItem addItem(DeviceId deviceId, String deviceName, EnergyConsumption consumption, Integer usageFrequency) {
        var item = new ReportItem(deviceId, deviceName, consumption, usageFrequency);
        this.items.add(item);
        this.totalConsumption = new EnergyConsumption(
                this.totalConsumption.value() + consumption.value(),
                this.totalConsumption.unit());
        return item;
    }

    /**
     * Replaces the list of items.
     * Used by the persistence assembler when reconstructing the aggregate.
     * @param items The list of report items.
     */
    public void setItems(List<ReportItem> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }
}
