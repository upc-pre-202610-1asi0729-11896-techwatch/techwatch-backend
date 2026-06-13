package com.techwatch.techwatchbackend.devices.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Space domain entity.
 *
 * <p>
 * Represents a single ambient (e.g. living room, bedroom, kitchen) inside a property.
 * A space is an internal entity of the {@code Property} aggregate and therefore has no
 * repository of its own; it is always accessed through its owning property.
 * </p>
 */
@Getter
public class Space {
    /**
     * The unique identifier for the space.
     */
    @Setter
    private Long id;

    /**
     * The name of the space.
     */
    @Setter
    private String name;

    /**
     * The description of the space.
     */
    @Setter
    private String description;

    /**
     * Constructor for Space.
     * @param name The name of the space.
     * @param description The description of the space.
     */
    public Space(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Default constructor for Space.
     */
    public Space() {
    }
}
