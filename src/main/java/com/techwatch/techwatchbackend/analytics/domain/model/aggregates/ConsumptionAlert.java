package com.techwatch.techwatchbackend.analytics.domain.model.aggregates;

import com.techwatch.techwatchbackend.analytics.domain.model.commands.TriggerConsumptionAlertCommand;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.AlertSeverity;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.DeviceId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.analytics.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ConsumptionAlert aggregate root.
 *
 * <p>Represents an alert raised when the consumption of a device exceeds a configured threshold.
 * No JPA or persistence annotation is present here -- those concerns live exclusively in
 * {@code ConsumptionAlertPersistenceEntity}.</p>
 */
@Getter
public class ConsumptionAlert extends AbstractDomainAggregateRoot<ConsumptionAlert> {
    /**
     * The unique identifier for the alert.
     */
    @Setter
    private Long id;

    /**
     * The id of the user the alert is for.
     */
    @Setter
    private UserId userId;

    /**
     * The id of the property the alert relates to.
     */
    @Setter
    private PropertyId propertyId;

    /**
     * The id of the device the alert relates to.
     */
    @Setter
    private DeviceId deviceId;

    /**
     * The severity of the alert.
     */
    @Setter
    private AlertSeverity severity;

    /**
     * A human-readable message describing the alert.
     */
    @Setter
    private String message;

    /**
     * The threshold that was exceeded.
     */
    @Setter
    private Double threshold;

    /**
     * The consumption value that triggered the alert.
     */
    @Setter
    private Double currentValue;

    /**
     * Whether the alert has been read by the user.
     */
    @Setter
    private Boolean isRead;

    /**
     * The moment the alert was triggered.
     */
    @Setter
    private LocalDateTime triggeredAt;

    /**
     * Default constructor for ConsumptionAlert.
     */
    public ConsumptionAlert() {
    }

    /**
     * Constructor for ConsumptionAlert with a TriggerConsumptionAlertCommand.
     * The alert starts unread with the current timestamp and a generated message.
     * @param command The {@link TriggerConsumptionAlertCommand}.
     */
    public ConsumptionAlert(TriggerConsumptionAlertCommand command) {
        this.userId = new UserId(command.userId());
        this.propertyId = new PropertyId(command.propertyId());
        this.deviceId = new DeviceId(command.deviceId());
        this.severity = command.severity();
        this.threshold = command.threshold();
        this.currentValue = command.currentValue();
        this.isRead = false;
        this.triggeredAt = LocalDateTime.now();
        this.message = "%s energy consumption detected on device %d: %.1f Wh (threshold %.1f Wh)"
                .formatted(command.severity(), command.deviceId(), command.currentValue(), command.threshold());
    }

    /**
     * Marks the alert as read.
     * @return the updated ConsumptionAlert instance.
     */
    public ConsumptionAlert markAsRead() {
        this.isRead = true;
        return this;
    }
}
