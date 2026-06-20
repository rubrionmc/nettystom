// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called when a new instance is set for an entity.
 */
// Type declaration (class/interface/enum/record)
public class EntitySpawnEvent implements EntityInstanceEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Instance spawnInstance;

    // Start of a method/block
    public EntitySpawnEvent(Entity entity, Instance spawnInstance) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.spawnInstance = spawnInstance;
    // End of a block/expression
    }

    /**
     * Gets the entity who spawned in the instance.
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
     * Gets the entity new instance.
     *
     * @return the instance
     */
    // Start of a method/block
    public Instance getSpawnInstance() {
        // Returns a value to the caller
        return spawnInstance;
    // End of a block/expression
    }

// End of a block/expression
}
