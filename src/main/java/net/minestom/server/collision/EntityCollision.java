// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
final class EntityCollision {
    // Start of a method/block
    static List<EntityCollisionResult> checkCollision(Instance instance, BoundingBox boundingBox, Point point, Vec entityVelocity, double extendRadius, Function<Entity, Boolean> entityFilter, @Nullable PhysicsResult physicsResult) {
        // Calls a method
        double minimumRes = physicsResult != null ? physicsResult.res().res : Double.MAX_VALUE;

        // Calls a method
        List<EntityCollisionResult> result = new ArrayList<>();

        // Calls a method
        var maxDistance = Math.pow(boundingBox.height() * boundingBox.height() + boundingBox.depth() / 2 * boundingBox.depth() / 2 + boundingBox.width() / 2 * boundingBox.width() / 2, 1 / 3.0);
        // Calls a method
        double projectileDistance = entityVelocity.length();

        // Loop: repeats a block
        for (Entity e : instance.getNearbyEntities(point, extendRadius + maxDistance + projectileDistance)) {
            // Calls a method
            SweepResult sweepResult = new SweepResult(minimumRes, 0, 0, 0, null, 0, 0, 0, 0, 0, 0);

            // Branch: checks a condition
            if (!entityFilter.apply(e)) continue;
            // Branch: checks a condition
            if (!e.hasEntityCollision()) continue;

            // Overlapping with entity, math can't be done we return the entity
            // Branch: checks a condition
            if (e.getBoundingBox().intersectBox(e.getPosition().sub(point), boundingBox)) {
                // Calls a method
                var p = point.asPos();
                // Calls a method
                result.add(new EntityCollisionResult(p, e, Vec.ZERO, 0));
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Check collisions with entity
            // Calls a method
            boolean intersected = e.getBoundingBox().intersectBoxSwept(point, entityVelocity, e.getPosition(), boundingBox, sweepResult);

            // Branch: checks a condition
            if (intersected && sweepResult.res < 1) {
                // Calls a method
                var p = point.asPos().add(entityVelocity.mul(sweepResult.res));
                // Calls a method
                Vec direction = new Vec(sweepResult.collidedPositionX, sweepResult.collidedPositionY, sweepResult.collidedPositionZ);
                // Calls a method
                result.add(new EntityCollisionResult(p, e, direction, sweepResult.res));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
