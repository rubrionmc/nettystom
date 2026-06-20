// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Represents an {@link EntityEvent} which happen in {@link Entity#getInstance()}.
 * Useful if you need to listen to entity events happening in its instance.
 * <p>
 * Be aware that the entity's instance must be non-null.
 */
// Type declaration (class/interface/enum/record)
public interface EntityInstanceEvent extends EntityEvent, InstanceEvent {
    // Annotation for the following element
    @Override
    // Start of a method/block
    default Instance getInstance() {
        // Calls a method
        final Instance instance = getEntity().getInstance();
        // Code statement
        assert instance != null : "EntityInstanceEvent is only supported on events where the entity's instance is non-null!";
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }
// End of a block/expression
}
