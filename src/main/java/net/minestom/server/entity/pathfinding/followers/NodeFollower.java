// Package declaration for this file
package net.minestom.server.entity.pathfinding.followers;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public interface NodeFollower {
    /**
     * Move towards the specified point
     *
     * @param target the point to move towards
     * @param speed  the speed to move at
     * @param lookAt the point to look at
     */
    // Calls a method
    void moveTowards(Point target, double speed, Point lookAt);

    /**
     * Jump
     */
    // Calls a method
    void jump(@Nullable Point point, @Nullable Point target);

    /**
     * Check if the follower is at the specified point
     * @param point the point to check
     * @return true if the follower is at the point
     */
    // Calls a method
    boolean isAtPoint(Point point);

    /**
     * Get the movement speed of the follower
     * @return the movement speed
     */
    // Calls a method
    double movementSpeed();
// End of a block/expression
}