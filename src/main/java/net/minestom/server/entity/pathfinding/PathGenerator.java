// Package declaration for this file
package net.minestom.server.entity.pathfinding;

// Import of a required class
import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;
// Import of a required class
import it.unimi.dsi.fastutil.objects.ObjectOpenHashBigSet;
// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.pathfinding.generators.NodeGenerator;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;

// Type declaration (class/interface/enum/record)
public final class PathGenerator {
    // Calls a method
    private static final Comparator<PNode> pNodeComparator = (s1, s2) -> (int) (((s1.g() + s1.h()) - (s2.g() + s2.h())) * 1000);

    // Code statement
    public static PPath generate(Block.Getter getter, Pos orgStart, Point orgTarget,
                                          // Code statement
                                          double closeDistance, double maxDistance, double pathVariance,
                                          // Code statement
                                          BoundingBox boundingBox, boolean isOnGround, NodeGenerator generator,
                                          // Annotation for the following element
                                          @Nullable Runnable onComplete) {
        // Assigns a value
        final Point start = (!isOnGround && generator.hasGravitySnap())
                // Code statement
                ? orgStart.withY(generator.gravitySnap(getter, orgStart.x(), orgStart.y(), orgStart.z(), boundingBox, 100).orElse(orgStart.y()))
                // Code statement
                : orgStart;

        // Assigns a value
        final Point target = (generator.hasGravitySnap())
                // Code statement
                ? orgTarget.withY(generator.gravitySnap(getter, orgTarget.x(), orgTarget.y(), orgTarget.z(), boundingBox, 100).orElse(orgTarget.y()))
                // Calls a method
                : orgTarget.asPos();

        // Calls a method
        PPath path = new PPath(maxDistance, pathVariance, onComplete);
        // Calls a method
        computePath(getter, start, target, closeDistance, maxDistance, pathVariance, boundingBox, path, generator);
        // Returns a value to the caller
        return path;
    // End of a block/expression
    }

    // Start of a method/block
    private static PNode buildRepathNode(PNode parent) {
        // Returns a value to the caller
        return new PNode(0, 0, 0, 0, 0, PNode.Type.REPATH, parent);
    // End of a block/expression
    }

    // Code statement
    private static void computePath(Block.Getter getter, Point start, Point target,
                                    // Code statement
                                    double closeDistance, double maxDistance, double pathVariance,
                                    // Start of a method/block
                                    BoundingBox boundingBox, PPath path, NodeGenerator generator) {
        // Assigns a value
        double closestDistance = Double.MAX_VALUE;
        // Calls a method
        double straightDistance = generator.heuristic(start, target);
        // Calls a method
        int maxSize = (int) Math.floor(maxDistance * 10);

        // Calls a method
        closeDistance = Math.max(0.8, closeDistance);
        // Calls a method
        List<PNode> closestFoundNodes = List.of();

        // Calls a method
        PNode pStart = new PNode(start, 0, generator.heuristic(start, target), PNode.Type.WALK, null);

        // Calls a method
        ObjectHeapPriorityQueue<PNode> open = new ObjectHeapPriorityQueue<>(pNodeComparator);
        // Calls a method
        open.enqueue(pStart);

        // Calls a method
        Set<PNode> closed = new ObjectOpenHashBigSet<>(maxSize);

        // Loop: repeats a block
        while (!open.isEmpty() && closed.size() < maxSize) {
            // Branch: checks a condition
            if (path.getState() == PPath.State.TERMINATING) {
                // Calls a method
                path.setState(PPath.State.TERMINATED);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            PNode current = open.dequeue();

            //var chunk = instance.getChunkAt(current.x(), current.z());
            //if (chunk == null) continue;
            //if (!chunk.isLoaded()) continue;

            // Branch: checks a condition
            if (((current.g() + current.h()) - straightDistance) > pathVariance) continue;
            // Branch: checks a condition
            if (!withinDistance(current, start, maxDistance)) continue;
            // Branch: checks a condition
            if (withinDistance(current, target, closeDistance)) {
                // Calls a method
                open.enqueue(current);
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (current.h() < closestDistance) {
                // Calls a method
                closestDistance = current.h();
                // Calls a method
                closestFoundNodes = List.of(current);
            // End of a block/expression
            }

            // Calls a method
            Collection<? extends PNode> found = generator.getWalkable(getter, closed, current, target, boundingBox);
            // Start of a method/block
            found.forEach(p -> {
                // Branch: checks a condition
                if (getDistanceSquared(p.x(), p.y(), p.z(), start) <= (maxDistance * maxDistance)) {
                    // Calls a method
                    open.enqueue(p);
                    // Calls a method
                    closed.add(p);
                // End of a block/expression
                }
            // End of a block/expression
            });
        // End of a block/expression
        }

        // Calls a method
        PNode current = open.isEmpty() ? null : open.dequeue();

        // Branch: checks a condition
        if (current == null || !withinDistance(current, target, closeDistance)) {
            // Branch: checks a condition
            if (closestFoundNodes.isEmpty()) {
                // Calls a method
                path.setState(PPath.State.INVALID);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            current = closestFoundNodes.getFirst();

            // Branch: checks a condition
            if (!open.isEmpty()) {
                // Calls a method
                current = buildRepathNode(current);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        while (current.parent() != null) {
            // Calls a method
            path.getNodes().add(current);
            // Calls a method
            current = current.parent();
        // End of a block/expression
        }

        // Calls a method
        Collections.reverse(path.getNodes());

        // Branch: checks a condition
        if (path.getCurrentType() == PNode.Type.REPATH) {
            // Calls a method
            path.setState(PPath.State.INVALID);
            // Calls a method
            path.getNodes().clear();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (path.getNodes().isEmpty()) {
            // Calls a method
            path.setState(PPath.State.INVALID);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        var lastNode = path.getNodes().getLast();
        // Branch: checks a condition
        if (getDistanceSquared(lastNode.x(), lastNode.y(), lastNode.z(), target) > (closeDistance * closeDistance)) {
            // Calls a method
            path.setState(PPath.State.BEST_EFFORT);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        PNode pEnd = new PNode(target, 0, 0, PNode.Type.WALK, null);
        // Calls a method
        path.getNodes().add(pEnd);
        // Calls a method
        path.setState(PPath.State.COMPUTED);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean withinDistance(PNode point, Point target, double closeDistance) {
        // Returns a value to the caller
        return getDistanceSquared(point.x(), point.y(), point.z(), target) < (closeDistance * closeDistance);
    // End of a block/expression
    }

    // Start of a method/block
    private static double getDistanceSquared(double x, double y, double z, Point target) {
        // Calls a method
        double dx = x - target.x();
        // Calls a method
        double dy = y - target.y();
        // Calls a method
        double dz = z - target.z();
        // Returns a value to the caller
        return dx * dx + dy * dy + dz * dz;
    // End of a block/expression
    }
// End of a block/expression
}
