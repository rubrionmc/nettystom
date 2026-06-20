// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called right before an entity is removed
 */
// Type declaration (class/interface/enum/record)
public class EntityDespawnEvent implements EntityInstanceEvent {

    // Code statement
    private final Entity entity;

    // Start of a method/block
    public EntityDespawnEvent(Entity entity) {
        // Access to the current/parent object
        this.entity = entity;
    // End of a block/expression
    }

    /**
     * Gets the entity who is about to be removed
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
// End of a block/expression
}
