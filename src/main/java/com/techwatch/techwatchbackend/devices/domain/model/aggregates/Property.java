package com.techwatch.techwatchbackend.devices.domain.model.aggregates;

import com.techwatch.techwatchbackend.devices.domain.model.commands.CreatePropertyCommand;
import com.techwatch.techwatchbackend.devices.domain.model.entities.Space;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.PropertyType;
import com.techwatch.techwatchbackend.devices.domain.model.valueobjects.UserId;
import com.techwatch.techwatchbackend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Property aggregate root.
 *
 * <p>
 * Represents an immovable property (a house or an apartment) owned by a user.
 * A property is organized into {@link Space}s, which are internal entities of this
 * aggregate. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code PropertyPersistenceEntity}.
 * </p>
 */
@Getter
public class Property extends AbstractDomainAggregateRoot<Property> {
    /**
     * The unique identifier for the property.
     */
    @Setter
    private Long id;

    /**
     * The id of the user who owns the property.
     */
    @Setter
    private UserId userId;

    /**
     * The name of the property.
     */
    @Setter
    private String name;

    /**
     * The address of the property.
     */
    @Setter
    private String address;

    /**
     * The type of the property.
     */
    @Setter
    private PropertyType type;

    /**
     * The spaces (ambients) the property is organized into.
     */
    private List<Space> spaces;

    /**
     * Default constructor for Property.
     * Initializes an empty list of spaces.
     */
    public Property() {
        this.spaces = new ArrayList<>();
    }

    /**
     * Constructor for Property with a CreatePropertyCommand.
     * @param command The {@link CreatePropertyCommand}.
     */
    public Property(CreatePropertyCommand command) {
        this.userId = new UserId(command.userId());
        this.name = command.name();
        this.address = command.address();
        this.type = command.type();
        this.spaces = new ArrayList<>();
    }

    /**
     * Updates the property information.
     * @param name The new name.
     * @param address The new address.
     * @param type The new type.
     * @return The updated Property instance.
     */
    public Property updateInformation(String name, String address, PropertyType type) {
        this.name = name;
        this.address = address;
        this.type = type;
        return this;
    }

    /**
     * Adds a new space to the property.
     * @param name The name of the space.
     * @param description The description of the space.
     * @return The created {@link Space}.
     */
    public Space addSpace(String name, String description) {
        var space = new Space(name, description);
        this.spaces.add(space);
        return space;
    }

    /**
     * Finds a space owned by this property by its name.
     * @param name The name of the space.
     * @return an {@link Optional} with the matching space, or empty if none matches.
     */
    public Optional<Space> getSpaceByName(String name) {
        return this.spaces.stream()
                .filter(space -> space.getName().equals(name))
                .findFirst();
    }

    /**
     * Replaces the list of spaces.
     * Used by the persistence assembler when reconstructing the aggregate.
     * @param spaces The list of spaces.
     */
    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces == null ? new ArrayList<>() : spaces;
    }
}
