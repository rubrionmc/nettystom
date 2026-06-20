// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when an entity ticks itself.
 * Same event instance used for all tick events for the same entity.
 */
// Type declaration (class/interface/enum/record)
public class EntityTickEvent implements EntityInstanceEvent {

    // Code statement
    private final Entity entity;

    // Start of a method/block
    public EntityTickEvent(Entity entity) {
        // Access to the current/parent object
        this.entity = entity;
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
