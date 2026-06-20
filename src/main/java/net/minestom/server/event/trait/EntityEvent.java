// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.Event;

/**
 * Represents any event called on an {@link Entity}.
 */
// Type declaration (class/interface/enum/record)
public interface EntityEvent extends Event {

    /**
     * Gets the entity of this event.
     *
     * @return the entity
     */
    // Calls a method
    Entity getEntity();
// End of a block/expression
}
