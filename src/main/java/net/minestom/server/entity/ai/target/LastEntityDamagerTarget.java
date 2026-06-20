// Package declaration for this file
package net.minestom.server.entity.ai.target;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.TargetSelector;
// Import of a required class
import net.minestom.server.entity.damage.Damage;
// Import of a required class
import net.minestom.server.entity.damage.EntityDamage;

/**
 * Targets the last damager of this entity.
 */
// Type declaration (class/interface/enum/record)
public class LastEntityDamagerTarget extends TargetSelector {

    // Code statement
    private final float range;

    // Start of a method/block
    public LastEntityDamagerTarget(EntityCreature entityCreature, float range) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.range = range;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity findTarget() {
        // Calls a method
        final Damage damage = entityCreature.getLastDamageSource();
        // Branch: checks a condition
        if (!(damage instanceof EntityDamage entityDamage)) {
            // No damager recorded, return null
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Calls a method
        final Entity entity = entityDamage.getSource();
        // Branch: checks a condition
        if (entity.isRemoved()) {
            // Entity not valid
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Check range
        // Returns a value to the caller
        return entityCreature.getDistanceSquared(entity) < range * range ? entity : null;
    // End of a block/expression
    }
// End of a block/expression
}
