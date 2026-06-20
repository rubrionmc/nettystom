// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

// Type declaration (class/interface/enum/record)
public class EntitySetFireEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private int ticks;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public EntitySetFireEvent(Entity entity, int ticks) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.ticks = ticks;
    // End of a block/expression
    }

    // Start of a method/block
    public int getFireTicks() {
        // Returns a value to the caller
        return ticks;
    // End of a block/expression
    }

    // Start of a method/block
    public void setFireTicks(int ticks) {
        // Access to the current/parent object
        this.ticks = ticks;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }
// End of a block/expression
}
