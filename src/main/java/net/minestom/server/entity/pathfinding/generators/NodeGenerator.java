// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding.generators;

// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.collision.CollisionUtils;
// Import d'une classe nécessaire
import net.minestom.server.collision.PhysicsResult;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.pathfinding.PNode;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.OptionalDouble;
// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
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
    // Instruction de code
    Collection<? extends PNode> getWalkable(Block.Getter getter, Set<PNode> visited,
                                                     // Instruction de code
                                                     PNode current, Point goal, BoundingBox boundingBox);

    /**
     * @return snap start and end points to the ground
     */
    // Appelle une méthode
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
    // Instruction de code
    OptionalDouble gravitySnap(Block.Getter getter, double pointX, double pointY, double pointZ,
                                        // Instruction de code
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
    // Début d'une méthode/d'un bloc
    default boolean canMoveTowards(Block.Getter getter, Point start, Point end, BoundingBox boundingBox) {
        // Appelle une méthode
        final Point diff = end.sub(start);

        // Embranchement : vérifie une condition
        if (getter.getBlock(end) != Block.AIR) return false;
        // Affecte une valeur
        PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox,
                // Appelle une méthode
                start.asPos(), diff.asVec(), null, false);
        // Renvoie une valeur à l'appelant
        return !res.collisionZ() && !res.collisionY() && !res.collisionX();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Check if the point is invalid
     *
     * @param getter
     * @param point
     * @param boundingBox
     * @return true if the point is invalid
     */
    // Début d'une méthode/d'un bloc
    default boolean pointInvalid(Block.Getter getter, Point point, BoundingBox boundingBox) {
        // Appelle une méthode
        var iterator = boundingBox.getBlocks(point);
        // Boucle : répète un bloc
        while (iterator.hasNext()) {
            // Appelle une méthode
            var block = iterator.next();
            // Embranchement : vérifie une condition
            if (getter.getBlock(block.blockX(), block.blockY(), block.blockZ(), Block.Getter.Condition.TYPE).isSolid()) {
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Heuristic use for the distance from the node to the target
     *
     * @param node
     * @param target
     * @return the heuristic
     */
    // Début d'une méthode/d'un bloc
    default double heuristic(Point node, Point target) {
        // Renvoie une valeur à l'appelant
        return node.distance(target);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
