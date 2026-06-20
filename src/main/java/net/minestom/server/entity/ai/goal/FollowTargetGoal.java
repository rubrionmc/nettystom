// Package declaration for this file
package net.minestom.server.entity.ai.goal;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.EntityCreature;
// Import of a required class
import net.minestom.server.entity.ai.GoalSelector;
// Import of a required class
import net.minestom.server.entity.pathfinding.Navigator;

// Import of a required class
import java.time.Duration;

// Type declaration (class/interface/enum/record)
public class FollowTargetGoal extends GoalSelector {
    // Code statement
    private final Duration pathDuration;
    // Assigns a value
    private long lastUpdateTime = 0;
    // Assigns a value
    private boolean forceEnd = false;
    // Code statement
    private Point lastTargetPos;

    // Code statement
    private Entity target;

    /**
     * Creates a follow target goal object.
     *
     * @param entityCreature the entity
     * @param pathDuration   the time between each path update (to check if the target moved)
     */
    // Start of a method/block
    public FollowTargetGoal(EntityCreature entityCreature, Duration pathDuration) {
        // Access to the current/parent object
        super(entityCreature);
        // Access to the current/parent object
        this.pathDuration = pathDuration;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldStart() {
        // Calls a method
        Entity target = entityCreature.getTarget();
        // Branch: checks a condition
        if (target == null) target = findTarget();
        // Branch: checks a condition
        if (target == null) return false;
        // Calls a method
        final boolean result = target.getPosition().distanceSquared(entityCreature.getPosition()) >= 2 * 2;
        // Branch: checks a condition
        if (result) {
            // Access to the current/parent object
            this.target = target;
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void start() {
        // Assigns a value
        lastUpdateTime = 0;
        // Assigns a value
        forceEnd = false;
        // Assigns a value
        lastTargetPos = null;
        // Branch: checks a condition
        if (target == null) {
            // No defined target
            // Access to the current/parent object
            this.forceEnd = true;
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Access to the current/parent object
        this.entityCreature.setTarget(target);
        // Calls a method
        Navigator navigator = entityCreature.getNavigator();
        // Access to the current/parent object
        this.lastTargetPos = target.getPosition();
        // Branch: checks a condition
        if (lastTargetPos.distanceSquared(entityCreature.getPosition()) < 2 * 2) {
            // Target is too far
            // Access to the current/parent object
            this.forceEnd = true;
            // Calls a method
            navigator.setPathTo(null);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (navigator.getPathPosition() == null || !navigator.getPathPosition().samePoint(lastTargetPos)) {
            // Calls a method
            navigator.setPathTo(lastTargetPos);
        // Alternative branch of the condition
        } else {
            // Assigns a value
            forceEnd = true;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void tick(long time) {
        // Branch: checks a condition
        if (forceEnd ||
                // Code statement
                pathDuration.isZero() ||
                // Start of a method/block
                pathDuration.toMillis() + lastUpdateTime > time) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        final Pos targetPos = entityCreature.getTarget() != null ? entityCreature.getTarget().getPosition() : null;
        // Branch: checks a condition
        if (targetPos != null && !targetPos.sameBlock(lastTargetPos)) {
            // Access to the current/parent object
            this.lastUpdateTime = time;
            // Access to the current/parent object
            this.lastTargetPos = targetPos;
            // Access to the current/parent object
            this.entityCreature.getNavigator().setPathTo(targetPos);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shouldEnd() {
        // Calls a method
        final Entity target = entityCreature.getTarget();
        // Returns a value to the caller
        return forceEnd ||
                // Code statement
                target == null ||
                // Code statement
                target.isRemoved() ||
                // Calls a method
                target.getPosition().distanceSquared(entityCreature.getPosition()) < 2 * 2;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void end() {
        // Access to the current/parent object
        this.entityCreature.getNavigator().setPathTo(null);
    // End of a block/expression
    }
// End of a block/expression
}
