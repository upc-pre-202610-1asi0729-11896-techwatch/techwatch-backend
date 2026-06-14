package com.techwatch.techwatchbackend.analytics.domain.model.aggregates;

import com.techwatch.techwatchbackend.analytics.domain.model.commands.CalculateMetricsCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.ConsumptionPeriod;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.EnergyConsumption;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.MetricType;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ConsumptionMetric aggregate root.
 *
 * <p>Represents a consumption metric calculated for a property (and optionally a specific device)
 * over a period of time. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code ConsumptionMetricPersistenceEntity}.</p>
 */
@Getter
public class ConsumptionMetric extends AbstractDomainAggregateRoot<ConsumptionMetric> {
    /**
     * The unique identifier for the metric.
     */
    @Setter
    private Long id;

    /**
     * The id of the property the metric belongs to.
     */
    @Setter
    private PropertyId propertyId;

    /**
     * The id of the device the metric belongs to (may be null for property-level metrics).
     */
    @Setter
    private DeviceId deviceId;

    /**
     * The type of metric.
     */
    @Setter
    private MetricType metricType;

    /**
     * The metric value.
     */
    @Setter
    private EnergyConsumption value;

    /**
     * The period the metric covers.
     */
    @Setter
    private ConsumptionPeriod period;

    /**
     * The moment the metric was calculated.
     */
    @Setter
    private LocalDateTime calculatedAt;

    /**
     * Default constructor for ConsumptionMetric.
     */
    public ConsumptionMetric() {
    }

    /**
     * Constructor for ConsumptionMetric with a CalculateMetricsCommand.
     * @param command The {@link CalculateMetricsCommand}.
     */
    public ConsumptionMetric(CalculateMetricsCommand command) {
        this.propertyId = new PropertyId(command.propertyId());
        this.deviceId = command.deviceId() == null ? null : new DeviceId(command.deviceId());
        this.metricType = command.metricType();
        this.value = new EnergyConsumption(command.value(), command.unit());
        this.period = new ConsumptionPeriod(command.recordedAt(), command.recordedAt());
        this.calculatedAt = LocalDateTime.now();
    }
}
