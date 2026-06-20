// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

/**
 * Called when a player does a left click on an entity or with
 * {@link net.minestom.server.entity.EntityCreature#attack(Entity)}.
 */
// Type declaration (class/interface/enum/record)
public class EntityAttackEvent implements EntityInstanceEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Entity target;

    // Start of a method/block
    public EntityAttackEvent(Entity source, Entity target) {
        // Access to the current/parent object
        this.entity = source;
        // Access to the current/parent object
        this.target = target;
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

    /**
     * @return the target of the attack
     */
    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return target;
    // End of a block/expression
    }
// End of a block/expression
}
