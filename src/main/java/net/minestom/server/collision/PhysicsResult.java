// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
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
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public record PhysicsResult(
        // Instruction de code
        Pos newPosition,
        // Instruction de code
        Vec newVelocity,
        // Instruction de code
        boolean isOnGround,
        // Instruction de code
        boolean collisionX,
        // Instruction de code
        boolean collisionY,
        // Instruction de code
        boolean collisionZ,
        // Instruction de code
        Vec originalDelta,
        // Annotation pour l'élément suivant
        @UnknownNullability Point @UnknownNullability [] collisionPoints,
        // Annotation pour l'élément suivant
        @UnknownNullability Shape @UnknownNullability [] collisionShapes,
        // Annotation pour l'élément suivant
        @UnknownNullability Point @UnknownNullability [] collisionShapePositions,
        // Instruction de code
        boolean hasCollision,
        // Instruction de code
        SweepResult res,
        // Instruction de code
        boolean cached
// Début d'une méthode/d'un bloc
) {
    // Début d'une méthode/d'un bloc
    public PhysicsResult(Pos newPosition, Vec newVelocity, boolean isOnGround, boolean collisionX, boolean collisionY, boolean collisionZ, Vec originalDelta, Point[] collisionPoints, Shape[] collisionShapes, Point[] collisionShapePositions, boolean hasCollision, SweepResult res) {
        // Appelle une méthode
        this(newPosition, newVelocity, isOnGround, collisionX, collisionY, collisionZ, originalDelta, collisionPoints, collisionShapes, collisionShapePositions, hasCollision, res, false);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
