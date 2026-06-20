// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when a velocity is applied to an entity using {@link Entity#setVelocity(Vec)}.
 */
// Type declaration (class/interface/enum/record)
public class EntityVelocityEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private Vec velocity;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public EntityVelocityEvent(Entity entity, Vec velocity) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.velocity = velocity;
    // End of a block/expression
    }

    /**
     * Gets the enity who will be affected by {@link #getVelocity()}.
     *
     * @return the entity
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }

    /**
     * Gets the velocity which will be applied.
     *
     * @return the velocity
     */
    // Start of a method/block
    public Vec getVelocity() {
        // Returns a value to the caller
        return velocity;
    // End of a block/expression
    }

    /**
     * Changes the applied velocity.
     *
     * @param velocity the new velocity
     */
    // Start of a method/block
    public void setVelocity(Vec velocity) {
        // Access to the current/parent object
        this.velocity = velocity;
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
