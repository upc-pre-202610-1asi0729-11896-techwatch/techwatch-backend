package com.techwatch.techwatchbackend.devices.domain.model.aggregates;

import com.techwatch.techwatchbackend.devices.domain.model.commands.StartSimulationSessionCommand;
import com.techwatch.techwatchbackend.devices.domain.model.entities.DeviceAction;
import com.techwatch.techwatchbackend.devices.domain.model.entities.UsageDataRecord;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyId;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.SessionStatus;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SimulationSession aggregate root.
 *
 * <p>
 * Represents a session in which a user simulates the operation of the devices of a property,
 * recording the actions executed ({@link DeviceAction}) and the usage data generated
 * ({@link UsageDataRecord}). Both are internal entities of this aggregate. No JPA or persistence
 * annotation is present here -- those concerns live exclusively in {@code SimulationSessionPersistenceEntity}.
 * </p>
 */
@Getter
public class SimulationSession extends AbstractDomainAggregateRoot<SimulationSession> {
    /**
     * The unique identifier for the simulation session.
     */
    @Setter
    private Long id;

    /**
     * The id of the user who owns the session.
     */
    @Setter
    private UserId userId;

    /**
     * The id of the property the session runs on.
     */
    @Setter
    private PropertyId propertyId;

    /**
     * The status of the session.
     */
    @Setter
    private SessionStatus status;

    /**
     * The moment the session started.
     */
    @Setter
    private LocalDateTime startedAt;

    /**
     * The moment the session ended, if it has ended.
     */
    @Setter
    private LocalDateTime endedAt;

    /**
     * The actions executed during the session.
     */
    private List<DeviceAction> actions;

    /**
     * The usage data records generated during the session.
     */
    private List<UsageDataRecord> usageData;

    /**
     * Default constructor for SimulationSession.
     * Initializes empty lists of actions and usage data.
     */
    public SimulationSession() {
        this.actions = new ArrayList<>();
        this.usageData = new ArrayList<>();
    }

    /**
     * Constructor for SimulationSession with a StartSimulationSessionCommand.
     * The session starts in the ACTIVE status with the current timestamp.
     * @param command The {@link StartSimulationSessionCommand}.
     */
    public SimulationSession(StartSimulationSessionCommand command) {
        this.userId = new UserId(command.userId());
        this.propertyId = new PropertyId(command.propertyId());
        this.status = SessionStatus.ACTIVE;
        this.startedAt = LocalDateTime.now();
        this.actions = new ArrayList<>();
        this.usageData = new ArrayList<>();
    }

    /**
     * Indicates whether the session is currently active.
     * @return true if the status is ACTIVE.
     */
    public boolean isActive() {
        return this.status == SessionStatus.ACTIVE;
    }

    /**
     * Replaces the list of device actions.
     * Used by the persistence assembler when reconstructing the aggregate.
     * @param actions The list of device actions.
     */
    public void setActions(List<DeviceAction> actions) {
        this.actions = actions == null ? new ArrayList<>() : actions;
    }

    /**
     * Replaces the list of usage data records.
     * Used by the persistence assembler when reconstructing the aggregate.
     * @param usageData The list of usage data records.
     */
    public void setUsageData(List<UsageDataRecord> usageData) {
        this.usageData = usageData == null ? new ArrayList<>() : usageData;
    }
}
