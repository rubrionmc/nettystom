// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;

/**
 * Represents the result of a collision with an entity
 * @param collisionPoint
 * @param entity
 * @param direction the direction of the collision. ex. Vec(-1, 0, 0) means the entity collided with the west face of the entity
 */
// Déclaration de type (classe/interface/enum/record)
public record EntityCollisionResult(
        // Instruction de code
        Point collisionPoint,
        // Instruction de code
        Entity entity,
        // Instruction de code
        Vec direction,
        // Instruction de code
        double percentage
// Début d'une méthode/d'un bloc
) implements Comparable<EntityCollisionResult> {
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int compareTo(EntityCollisionResult o) {
        // Renvoie une valeur à l'appelant
        return Double.compare(percentage, o.percentage);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
