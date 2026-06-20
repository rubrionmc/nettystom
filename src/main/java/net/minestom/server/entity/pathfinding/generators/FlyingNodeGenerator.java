// Package declaration for this file
package net.minestom.server.entity.pathfinding.generators;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.pathfinding.PNode;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.OptionalDouble;
// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public class FlyingNodeGenerator implements NodeGenerator {
    // Assigns a value
    private PNode tempNode = null;

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<? extends PNode> getWalkable(Block.Getter getter, Set<PNode> visited, PNode current, Point goal, BoundingBox boundingBox) {
        // Calls a method
        Collection<PNode> nearby = new ArrayList<>();
        // Calls a method
        tempNode = new PNode(0, 0, 0, 0, 0, current);

        // Calls a method
        int stepSize = (int) Math.max(Math.floor(boundingBox.width() / 2), 1);
        // Branch: checks a condition
        if (stepSize < 1) stepSize = 1;

        // Loop: repeats a block
        for (int x = -stepSize; x <= stepSize; ++x) {
            // Loop: repeats a block
            for (int z = -stepSize; z <= stepSize; ++z) {
                // Branch: checks a condition
                if (x == 0 && z == 0) continue;
                // Calls a method
                double cost = Math.sqrt(x * x + z * z) * 0.98;

                // Calls a method
                double currentLevelPointX = current.blockX() + 0.5 + x;
                // Calls a method
                double currentLevelPointY = current.blockY() + 0.5;
                // Calls a method
                double currentLevelPointZ = current.blockZ() + 0.5 + z;

                // Calls a method
                double upPointX = current.blockX() + 0.5 + x;
                // Calls a method
                double upPointY = current.blockY() + 1 + 0.5;
                // Calls a method
                double upPointZ = current.blockZ() + 0.5 + z;

                // Calls a method
                double downPointX = current.blockX() + 0.5 + x;
                // Calls a method
                double downPointY = current.blockY() - 1 + 0.5;
                // Calls a method
                double downPointZ = current.blockZ() + 0.5 + z;

                // Calls a method
                var nodeWalk = createFly(getter, new Vec(currentLevelPointX, currentLevelPointY, currentLevelPointZ), boundingBox, cost, current, goal, visited);
                // Branch: checks a condition
                if (nodeWalk != null && !visited.contains(nodeWalk)) nearby.add(nodeWalk);

                // Calls a method
                var nodeJump = createFly(getter, new Vec(upPointX, upPointY, upPointZ), boundingBox, cost, current, goal, visited);
                // Branch: checks a condition
                if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);

                // Calls a method
                var nodeFall = createFly(getter, new Vec(downPointX, downPointY, downPointZ), boundingBox, cost, current, goal, visited);
                // Branch: checks a condition
                if (nodeFall != null && !visited.contains(nodeFall)) nearby.add(nodeFall);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Straight up
        // Calls a method
        double upPointX = current.x();
        // Calls a method
        double upPointY = current.blockY() + 1 + 0.5;
        // Calls a method
        double upPointZ = current.z();

        // Calls a method
        var nodeJump = createFly(getter, new Vec(upPointX, upPointY, upPointZ), boundingBox, 2, current, goal, visited);
        // Branch: checks a condition
        if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);

        // Straight down
        // Calls a method
        double downPointX = current.x();
        // Calls a method
        double downPointY = current.blockY() - 1 + 0.5;
        // Calls a method
        double downPointZ = current.z();

        // Calls a method
        var nodeFall = createFly(getter, new Vec(downPointX, downPointY, downPointZ), boundingBox, 2, current, goal, visited);
        // Branch: checks a condition
        if (nodeFall != null && !visited.contains(nodeFall)) nearby.add(nodeFall);

        // Returns a value to the caller
        return nearby;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean hasGravitySnap() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private PNode createFly(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Calls a method
        var n = newNode(start, cost, point, goal);
        // Branch: checks a condition
        if (closed.contains(n)) return null;
        // Branch: checks a condition
        if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), point, boundingBox)) return null;
        // Calls a method
        n.setType(PNode.Type.FLY);
        // Returns a value to the caller
        return n;
    // End of a block/expression
    }

    // Start of a method/block
    private PNode newNode(PNode current, double cost, Point point, Point goal) {
        // Calls a method
        tempNode.setG(current.g() + cost);
        // Calls a method
        tempNode.setH(heuristic(point, goal));
        // Calls a method
        tempNode.setPoint(point.x(), point.y(), point.z());

        // Assigns a value
        var newNode = tempNode;
        // Calls a method
        tempNode = new PNode(0, 0, 0, 0, 0, PNode.Type.WALK, current);

        // Returns a value to the caller
        return newNode;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public OptionalDouble gravitySnap(Block.Getter getter, double pointX, double pointY, double pointZ, BoundingBox boundingBox, double maxFall) {
        // Returns a value to the caller
        return OptionalDouble.of(pointY);
    // End of a block/expression
    }
// End of a block/expression
}
