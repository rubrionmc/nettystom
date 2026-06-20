// Package declaration for this file
package net.minestom.server.entity.ai;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * The target selector is called each time the entity receives an "attack" instruction
 * without having a target.
 */
// Type declaration (class/interface/enum/record)
public abstract class TargetSelector {

    // Code statement
    protected final EntityCreature entityCreature;

    // Start of a method/block
    public TargetSelector(EntityCreature entityCreature) {
        // Access to the current/parent object
        this.entityCreature = entityCreature;
    // End of a block/expression
    }

    /**
     * Finds the target.
     * <p>
     * Returning null means that this target selector didn't find any entity,
     * the next {@link TargetSelector} will be called until the end of the list or an entity is found.
     *
     * @return the target, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Calls a method
    public abstract Entity findTarget();

    /**
     * Gets the entity linked to this target selector.
     *
     * @return the entity
     */
    // Start of a method/block
    public EntityCreature getEntityCreature() {
        // Returns a value to the caller
        return entityCreature;
    // End of a block/expression
    }
// End of a block/expression
}
