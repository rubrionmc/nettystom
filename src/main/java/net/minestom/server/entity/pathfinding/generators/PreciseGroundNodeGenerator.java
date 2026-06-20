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
import net.minestom.server.coordinate.Pos;
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
public class PreciseGroundNodeGenerator implements NodeGenerator {
    // Assigns a value
    private PNode tempNode = null;
    // Assigns a value
    private final static int MAX_FALL_DISTANCE = 5;

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
                double floorPointX = current.blockX() + 0.5 + x;
                // Calls a method
                double floorPointY = current.y();
                // Calls a method
                double floorPointZ = current.blockZ() + 0.5 + z;

                // Calls a method
                var optionalFloorPointY = gravitySnap(getter, floorPointX, floorPointY, floorPointZ, boundingBox, MAX_FALL_DISTANCE);
                // Branch: checks a condition
                if (optionalFloorPointY.isEmpty()) continue;
                // Calls a method
                floorPointY = optionalFloorPointY.getAsDouble();

                // Calls a method
                var floorPoint = new Vec(floorPointX, floorPointY, floorPointZ);
                // Calls a method
                var nodeWalk = createWalk(getter, floorPoint, boundingBox, cost, current, goal, visited);

                // Branch: checks a condition
                if (nodeWalk != null && !visited.contains(nodeWalk)) nearby.add(nodeWalk);

                // Loop: repeats a block
                for (int i = 1; i <= 1; ++i) {
                    // Calls a method
                    Point jumpPoint = new Vec(current.blockX() + 0.5 + x, current.y() + i, current.blockZ() + 0.5 + z);
                    // Calls a method
                    OptionalDouble jumpPointY = gravitySnap(getter, jumpPoint.x(), jumpPoint.y(), jumpPoint.z(), boundingBox, MAX_FALL_DISTANCE);
                    // Branch: checks a condition
                    if (jumpPointY.isEmpty()) continue;
                    // Calls a method
                    jumpPoint = jumpPoint.withY(jumpPointY.getAsDouble());

                    // Branch: checks a condition
                    if (!floorPoint.sameBlock(jumpPoint)) {
                        // Calls a method
                        var nodeJump = createJump(getter, jumpPoint, boundingBox, cost + 0.8, current, goal, visited);
                        // Branch: checks a condition
                        if (nodeJump != null && !visited.contains(nodeJump)) nearby.add(nodeJump);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return nearby;
    // End of a block/expression
    }

    // Start of a method/block
    private PNode createWalk(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Calls a method
        var snapped = gravitySnap(getter, point.x(), point.y(), point.z(), boundingBox, MAX_FALL_DISTANCE);

        // Branch: checks a condition
        if (snapped.isPresent()) {
            // Calls a method
            var snappedPoint = new Vec(point.x(), snapped.getAsDouble(), point.z());

            // Calls a method
            var n = newNode(start, cost, snappedPoint, goal);
            // Branch: checks a condition
            if (closed.contains(n)) {
                // Returns a value to the caller
                return null;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (Math.abs(snappedPoint.y() - start.y()) > Vec.EPSILON && snappedPoint.y() < start.y()) {
                // Branch: checks a condition
                if (start.y() - snappedPoint.y() > MAX_FALL_DISTANCE) {
                    // Returns a value to the caller
                    return null;
                // End of a block/expression
                }
                // Branch: checks a condition
                if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), snappedPoint.withY(start.y()), boundingBox)) {
                    // Returns a value to the caller
                    return null;
                // End of a block/expression
                }
                // Calls a method
                n.setType(PNode.Type.FALL);
            // Alternative branch of the condition
            } else {
                // Branch: checks a condition
                if (!canMoveTowards(getter, new Vec(start.x(), start.y(), start.z()), snappedPoint, boundingBox)) {
                    // Returns a value to the caller
                    return null;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Returns a value to the caller
            return n;
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private PNode createJump(Block.Getter getter, Point point, BoundingBox boundingBox, double cost, PNode start, Point goal, Set<PNode> closed) {
        // Branch: checks a condition
        if (Math.abs(point.y() - start.y()) < Vec.EPSILON) return null;
        // Branch: checks a condition
        if (point.y() - start.y() > 2) return null;
        // Branch: checks a condition
        if (point.blockX() != start.blockX() && point.blockZ() != start.blockZ()) return null;

        // Calls a method
        var n = newNode(start, cost, point, goal);
        // Branch: checks a condition
        if (closed.contains(n)) return null;

        // Branch: checks a condition
        if (pointInvalid(getter, point, boundingBox)) return null;
        // Branch: checks a condition
        if (pointInvalid(getter, new Vec(start.x(), start.y() + 1, start.z()), boundingBox)) return null;

        // Calls a method
        n.setType(PNode.Type.JUMP);
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
    public boolean hasGravitySnap() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public OptionalDouble gravitySnap(Block.Getter getter, double pointOrgX, double pointOrgY, double pointOrgZ, BoundingBox boundingBox, double maxFall) {
        // Calls a method
        final double pointX = (int) Math.floor(pointOrgX) + 0.5;
        // Calls a method
        final double pointZ = (int) Math.floor(pointOrgZ) + 0.5;
        // Assigns a value
        final PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox,
                // Creates a new object
                new Pos(pointX, pointOrgY, pointZ), new Vec(0, -MAX_FALL_DISTANCE, 0),
                // Code statement
                null, true);
        // Returns a value to the caller
        return OptionalDouble.of(res.newPosition().y());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean canMoveTowards(Block.Getter getter, Point startOrg, Point endOrg, BoundingBox boundingBox) {
        // Calls a method
        final Point end = endOrg.add(0, Vec.EPSILON, 0);
        // Calls a method
        final Point start = startOrg.add(0, Vec.EPSILON, 0);
        // Calls a method
        final Point diff = end.sub(start);
        // Calls a method
        PhysicsResult res = CollisionUtils.handlePhysics(getter, boundingBox, start.asPos(), diff.asVec(), null, false);
        // Returns a value to the caller
        return !res.collisionZ() && !res.collisionY() && !res.collisionX();
    // End of a block/expression
    }
// End of a block/expression
}
