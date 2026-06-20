// Package declaration for this file
package net.minestom.server.event.entity.projectile;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.event.trait.RecursiveEvent;

// Type declaration (class/interface/enum/record)
class ProjectileCollideEvent implements EntityInstanceEvent, CancellableEvent, RecursiveEvent {

    // Code statement
    private final Entity projectile;
    // Code statement
    private final Pos position;
    // Code statement
    private boolean cancelled;

    // Start of a method/block
    protected ProjectileCollideEvent(Entity projectile, Pos position) {
        // Access to the current/parent object
        this.projectile = projectile;
        // Access to the current/parent object
        this.position = position;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return projectile;
    // End of a block/expression
    }

    // Start of a method/block
    public Pos getCollisionPosition() {
        // Returns a value to the caller
        return position;
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
        // Assigns a value
        cancelled = cancel;
    // End of a block/expression
    }
// End of a block/expression
}
