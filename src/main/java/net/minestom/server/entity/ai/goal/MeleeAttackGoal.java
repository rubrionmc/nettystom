// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;
// Import of a required class
import net.minestom.server.entity.ai.TargetSelector;
// Import of a required class
import net.minestom.server.entity.pathfinding.Navigator;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;

/**
 * Attacks the entity's target ({@link EntityCreature#getTarget()}) OR the closest entity
 * which can be targeted with the entity {@link TargetSelector}.
 */
// Type declaration (class/interface/enum/record)
public class MeleeAttackGoal extends GoalSelector {

    // Calls a method
    private final Cooldown cooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Code statement
    private long lastHit;
    // Code statement
    private final double range;
    // Code statement
    private final Duration delay;

    // Code statement
    private boolean stop;
    // Code statement
    private Entity cachedTarget;

    /**
     * @param entityCreature the entity to add the goal to
     * @param range          the allowed range the entity can attack others.
     * @param delay          the delay between each attacks
     * @param timeUnit       the unit of the delay
     */
    // Start of a method/block
    public MeleeAttackGoal(EntityCreature entityCreature, double range, int delay, TemporalUnit timeUnit) {
        // Calls a method
        this(entityCreature, range, Duration.of(delay, timeUnit));
    // End of a block/expression
    }

    /**
     * @param entityCreature the entity to add the goal to
     * @param range          the allowed range the entity can attack others.
     * @param delay          the delay between each attacks
     */
    // Start of a method/block
    public MeleeAttackGoal(EntityCreature entityCreature, double range, Duration delay) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.range = range;
        // Access to the current/parent object
        this.delay = delay;
    // End of a block/expression
    }

    // Start of a method/block
    public Cooldown getCooldown() {
        // Returns a value to the caller
        return this.cooldown;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Access to the current/parent object
        this.cachedTarget = findTarget();
        // Returns a value to the caller
        return this.cachedTarget != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Calls a method
        final Point targetPosition = this.cachedTarget.getPosition();
        // Calls a method
        entityCreature.getNavigator().setPathTo(targetPosition);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Code statement
        Entity target;
        // Branch: checks a condition
        if (this.cachedTarget != null) {
            // Assigns a value
            target = this.cachedTarget;
            // Access to the current/parent object
            this.cachedTarget = null;
        // Alternative branch of the condition
        } else {
            // Calls a method
            target = findTarget();
        // End of a block/expression
        }

        // Access to the current/parent object
        this.stop = target == null;

        // Branch: checks a condition
        if (!stop) {

            // Attack the target entity
            // Branch: checks a condition
            if (entityCreature.getDistanceSquared(target) <= range * range) {
                // Calls a method
                entityCreature.lookAt(target);
                // Branch: checks a condition
                if (!Cooldown.hasCooldown(time, lastHit, delay)) {
                    // Calls a method
                    entityCreature.attack(target, true);
                    // Access to the current/parent object
                    this.lastHit = time;
                // End of a block/expression
                }
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Move toward the target entity
            // Calls a method
            Navigator navigator = entityCreature.getNavigator();
            // Calls a method
            final var pathPosition = navigator.getPathPosition();
            // Calls a method
            final var targetPosition = target.getPosition();
            // Branch: checks a condition
            if (pathPosition == null || !pathPosition.samePoint(targetPosition)) {
                // Branch: checks a condition
                if (this.cooldown.isReady(time)) {
                    // Access to the current/parent object
                    this.cooldown.refreshLastUpdate(time);
                    // Calls a method
                    navigator.setPathTo(targetPosition);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Returns a value to the caller
        return stop;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
        // Stop following the target
        // Calls a method
        entityCreature.getNavigator().setPathTo(null);
    // End of a block/expression
    }
// End of a block/expression
}
