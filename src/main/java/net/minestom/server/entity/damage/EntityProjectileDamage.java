// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Represents damage inflicted by an entity, via a projectile.
 */
// Type declaration (class/interface/enum/record)
public class EntityProjectileDamage extends Damage {

    // Start of a method/block
    public EntityProjectileDamage(@Nullable Entity shooter, Entity projectile, float amount) {
        // Access to the current/parent object
        super(DamageType.MOB_PROJECTILE, projectile, shooter, null, amount);
    // End of a block/expression
    }

    /**
     * Gets the projectile responsible for the damage.
     *
     * @return the projectile
     */
    // Start of a method/block
    public Entity getProjectile() {
        // Returns a value to the caller
        return getSource();
    // End of a block/expression
    }

    /**
     * Gets the shooter of the projectile.
     *
     * @return the shooter of the projectile, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Entity getShooter() {
        // Returns a value to the caller
        return getAttacker();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getSource() {
        // Returns a value to the caller
        return super.getSource();
    // End of a block/expression
    }
// End of a block/expression
}