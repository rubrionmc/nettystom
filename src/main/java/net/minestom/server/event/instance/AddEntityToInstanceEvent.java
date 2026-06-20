// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityEvent;
// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called by an Instance when an entity is added to it.
 * Can be used attach data.
 */
// Type declaration (class/interface/enum/record)
public class AddEntityToInstanceEvent implements InstanceEvent, EntityEvent, CancellableEvent {

    // Code statement
    private final Instance instance;
    // Code statement
    private final Entity entity;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public AddEntityToInstanceEvent(Instance instance, Entity entity) {
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
     * Entity being added.
     *
     * @return the entity being added
     */
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }
// End of a block/expression
}
