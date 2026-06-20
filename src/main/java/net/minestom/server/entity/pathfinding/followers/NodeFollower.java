// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding.followers;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public interface NodeFollower {
    /**
     * Move towards the specified point
     *
     * @param target the point to move towards
     * @param speed  the speed to move at
     * @param lookAt the point to look at
     */
    // Appelle une méthode
    void moveTowards(Point target, double speed, Point lookAt);

    /**
     * Jump
     */
    // Appelle une méthode
    void jump(@Nullable Point point, @Nullable Point target);

    /**
     * Check if the follower is at the specified point
     * @param point the point to check
     * @return true if the follower is at the point
     */
    // Appelle une méthode
    boolean isAtPoint(Point point);

    /**
     * Get the movement speed of the follower
     * @return the movement speed
     */
    // Appelle une méthode
    double movementSpeed();
// Fin d'un bloc/d'une expression
}