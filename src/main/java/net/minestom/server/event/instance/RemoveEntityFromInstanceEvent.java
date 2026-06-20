// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called by an Instance when an entity is removed from it.
 */
// Type declaration (class/interface/enum/record)
public class RemoveEntityFromInstanceEvent implements EntityInstanceEvent {
    // Code statement
    private final Instance instance;
    // Code statement
    private final Entity entity;

    // Start of a method/block
    public RemoveEntityFromInstanceEvent(Instance instance, Entity entity) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.entity = entity;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Gets the entity being removed.
     *
     * @return entity being removed
     */
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }
// End of a block/expression
}
