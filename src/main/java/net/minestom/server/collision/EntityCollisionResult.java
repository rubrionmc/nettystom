// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.Entity;

/**
 * Represents the result of a collision with an entity
 * @param collisionPoint
 * @param entity
 * @param direction the direction of the collision. ex. Vec(-1, 0, 0) means the entity collided with the west face of the entity
 */
// Type declaration (class/interface/enum/record)
public record EntityCollisionResult(
        // Code statement
        Point collisionPoint,
        // Code statement
        Entity entity,
        // Code statement
        Vec direction,
        // Code statement
        double percentage
// Start of a method/block
) implements Comparable<EntityCollisionResult> {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public int compareTo(EntityCollisionResult o) {
        // Returns a value to the caller
        return Double.compare(percentage, o.percentage);
    // End of a block/expression
    }
// End of a block/expression
}
