// Package declaration for this file
package net.minestom.server.utils.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityType;

// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public final class EntityUtils {
    // Assigns a value
    private static final Set<EntityType> SITTING_ENTITIES = Set.of(EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED,
            // Code statement
            EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE,
            // Code statement
            EntityType.ZOMBIFIED_PIGLIN);

    /**
     * @param vehicle the target vehicle
     * @param passenger the target passenger
     * @return the height offset for the passenger of this vehicle
     */
    // Start of a method/block
    public static double getPassengerHeightOffset(Entity vehicle, Entity passenger) {
        // TODO: Refactor this in 1.20.5
        // Branch: checks a condition
        if (vehicle.getEntityType().name().contains("boat")) return -0.1;
        // Branch: checks a condition
        if (vehicle.getEntityType() == EntityType.MINECART) return 0.0;
        // Branch: checks a condition
        if (SITTING_ENTITIES.contains(passenger.getEntityType()))
            // Returns a value to the caller
            return vehicle.getBoundingBox().height() * 0.75;
        // Returns a value to the caller
        return vehicle.getBoundingBox().height();
    // End of a block/expression
    }

    // Start of a method/block
    private EntityUtils() {
    // End of a block/expression
    }
// End of a block/expression
}
