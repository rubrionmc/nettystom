// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.minestom.server.entity.Entity;

/**
 * Represents damage inflicted by an {@link Entity}.
 */
// Type declaration (class/interface/enum/record)
public class EntityDamage extends Damage {

    // Start of a method/block
    public EntityDamage(Entity source, float amount) {
        // Access to the current/parent object
        super(DamageType.MOB_ATTACK, source, source, null, amount);
    // End of a block/expression
    }

    /**
     * Gets the source of the damage.
     *
     * @return the source
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getSource() {
        // Returns a value to the caller
        return super.getSource();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getAttacker() {
        // Returns a value to the caller
        return getSource();
    // End of a block/expression
    }
// End of a block/expression
}