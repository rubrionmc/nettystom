// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.registry.RegistryKey;

/**
 * Represents damage that is associated with a certain position.
 */
// Type declaration (class/interface/enum/record)
public class PositionalDamage extends Damage {

    // Start of a method/block
    public PositionalDamage(RegistryKey<DamageType> type, Point sourcePosition, float amount) {
        // Access to the current/parent object
        super(type, null, null, sourcePosition, amount);
    // End of a block/expression
    }

// End of a block/expression
}