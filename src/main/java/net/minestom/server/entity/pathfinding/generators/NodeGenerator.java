// Package declaration for this file
package net.minestom.server.entity.pathfinding.generators;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.collision.CollisionUtils;
// Import of a required class
import net.minestom.server.collision.PhysicsResult;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.pathfinding.PNode;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.OptionalDouble;
// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public interface NodeGenerator {
    /**
     * Gets the walkable nodes.
     *
     * @param getter      the instance
     * @param visited     the visited nodes
     * @param current     the current node
     * @param goal        the goal
     * @param boundingBox the bounding box
     * @return the walkable nodes
     */
    // Code statement
    Collection<? extends PNode> getWalkable(Block.Getter getter, Set<PNode> visited,
                                                     // Code statement
                                                     PNode current, Point goal, BoundingBox boundingBox);

    /**
     * @return snap start and end points to the ground
     */
    // Calls a method
    boolean hasGravitySnap();

    /**
     * Snap point to the ground
     *
     * @param getter      the block getter
     * @param pointX      the x coordinate
     * @param pointY      the y coordinate
     * @param pointZ      the z coordinate
     * @param boundingBox the bounding box
     * @param maxFall     the maximum fall distance
     * @return the snapped y coordinate. Empty if the snap point is not found
     */
    // Code statement
    OptionalDouble gravitySnap(Block.Getter getter, double pointX, double pointY, double pointZ,
                                        // Code statement
                                        BoundingBox boundingBox, double maxFall);

    /**
     * Check if we can move directly from one point to another
     *
     * @param getter
     * @param start
     * @param end
     * @param boundingBox
     * @return true if we can move directly from start to end
     */
    // Start of a method/block
    default boolean canMoveTowards(Block.Getter getter, Point start, Point end, BoundingBox boundingBox) {
        // Calls a method
        final Point diff = end.sub(start);

        // Branch: checks a condition
        if (getter.getBlock(end) != Block.AIR) return false;
        // Assigns a value
        PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox,
                // Calls a method
                start.asPos(), diff.asVec(), null, false);
        // Returns a value to the caller
        return !res.collisionZ() && !res.collisionY() && !res.collisionX();
    // End of a block/expression
    }

    /**
     * Check if the point is invalid
     *
     * @param getter
     * @param point
     * @param boundingBox
     * @return true if the point is invalid
     */
    // Start of a method/block
    default boolean pointInvalid(Block.Getter getter, Point point, BoundingBox boundingBox) {
        // Calls a method
        var iterator = boundingBox.getBlocks(point);
        // Loop: repeats a block
        while (iterator.hasNext()) {
            // Calls a method
            var block = iterator.next();
            // Branch: checks a condition
            if (getter.getBlock(block.blockX(), block.blockY(), block.blockZ(), Block.Getter.Condition.TYPE).isSolid()) {
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Heuristic use for the distance from the node to the target
     *
     * @param node
     * @param target
     * @return the heuristic
     */
    // Start of a method/block
    default double heuristic(Point node, Point target) {
        // Returns a value to the caller
        return node.distance(target);
    // End of a block/expression
    }
// End of a block/expression
}
