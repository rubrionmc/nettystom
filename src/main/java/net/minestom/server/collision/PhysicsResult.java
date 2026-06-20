// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

/**
 * The result of a physics simulation.
 * @param newPosition the new position of the entity
 * @param newVelocity the new velocity of the entity
 * @param isOnGround if the entity is on the ground
 * @param collisionX if the entity collided on the X axis
 * @param collisionY if the entity collided on the Y axis
 * @param collisionZ if the entity collided on the Z axis
 * @param originalDelta the velocity delta of the entity
 * @param collisionPoints the points where the entity collided
 * @param collisionShapes the shapes the entity collided with
 * @param collisionShapePositions the positions of the shapes the entity collided with
 * @param hasCollision if the entity collided
 * @param res sweep result of the collision
 * @param cached if the result was due to quickly exiting
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public record PhysicsResult(
        // Code statement
        Pos newPosition,
        // Code statement
        Vec newVelocity,
        // Code statement
        boolean isOnGround,
        // Code statement
        boolean collisionX,
        // Code statement
        boolean collisionY,
        // Code statement
        boolean collisionZ,
        // Code statement
        Vec originalDelta,
        // Annotation for the following element
        @UnknownNullability Point @UnknownNullability [] collisionPoints,
        // Annotation for the following element
        @UnknownNullability Shape @UnknownNullability [] collisionShapes,
        // Annotation for the following element
        @UnknownNullability Point @UnknownNullability [] collisionShapePositions,
        // Code statement
        boolean hasCollision,
        // Code statement
        SweepResult res,
        // Code statement
        boolean cached
// Start of a method/block
) {
    // Start of a method/block
    public PhysicsResult(Pos newPosition, Vec newVelocity, boolean isOnGround, boolean collisionX, boolean collisionY, boolean collisionZ, Vec originalDelta, Point[] collisionPoints, Shape[] collisionShapes, Point[] collisionShapePositions, boolean hasCollision, SweepResult res) {
        // Calls a method
        this(newPosition, newVelocity, isOnGround, collisionX, collisionY, collisionZ, originalDelta, collisionPoints, collisionShapes, collisionShapePositions, hasCollision, res, false);
    // End of a block/expression
    }
// End of a block/expression
}
