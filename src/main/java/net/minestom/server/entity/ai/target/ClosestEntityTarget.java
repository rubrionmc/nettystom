// Package declaration for this file
package net.minestom.server.entity.ai.target;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.ai.TargetSelector;
// Import of a required class
import net.minestom.server.instance.Instance;

// Import of a required class
import java.util.Comparator;
// Import of a required class
import java.util.function.Predicate;

/**
 * Target the closest targetable entity (based on the target predicate)
 */
// Type declaration (class/interface/enum/record)
public class ClosestEntityTarget extends TargetSelector {

    // Code statement
    private final double range;
    // Code statement
    private final Predicate<Entity> targetPredicate;

    /**
     * @param entityCreature the entity (self)
     * @param range          the maximum range the entity can target others within
     * @param entitiesTarget the entities to target by class
     * @deprecated Use {@link #ClosestEntityTarget(EntityCreature, double, Predicate)}
     */
    // Annotation for the following element
    @SafeVarargs
    // Annotation for the following element
    @Deprecated
    // Code statement
    public ClosestEntityTarget(EntityCreature entityCreature, float range,
                               // Start of a method/block
                               Class<? extends LivingEntity>... entitiesTarget) {
        // Start of a method/block
        this(entityCreature, range, ent -> {
            // Calls a method
            Class<? extends Entity> clazz = ent.getClass();
            // Loop: repeats a block
            for (Class<? extends LivingEntity> targetClass : entitiesTarget) {
                // Branch: checks a condition
                if (targetClass.isAssignableFrom(clazz)) {
                    // Returns a value to the caller
                    return true;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return false;
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * @param entityCreature  the entity (self)
     * @param range           the maximum range the entity can target others within
     * @param targetPredicate the predicate used to check if the other entity can be targeted
     */
    // Code statement
    public ClosestEntityTarget(EntityCreature entityCreature, double range,
                               // Start of a method/block
                               Predicate<Entity> targetPredicate) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.range = range;
        // Access to the current/parent object
        this.targetPredicate = targetPredicate;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity findTarget() {

        // Calls a method
        Instance instance = entityCreature.getInstance();

        // Branch: checks a condition
        if (instance == null) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Returns a value to the caller
        return instance.getNearbyEntities(entityCreature.getPosition(), range).stream()
                // Don't target our self and make sure entity is valid
                // Code statement
                .filter(ent -> !entityCreature.equals(ent) && !ent.isRemoved())
                // Code statement
                .filter(targetPredicate)
                // Code statement
                .min(Comparator.comparingDouble(e -> e.getDistanceSquared(entityCreature)))
                // Calls a method
                .orElse(null);

    // End of a block/expression
    }

// End of a block/expression
}
