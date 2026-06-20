// Package declaration for this file
package net.minestom.server.entity.pathfinding;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Type declaration (class/interface/enum/record)
public final class PPath {
    // Code statement
    private final Runnable onComplete;
    // Calls a method
    private final List<PNode> nodes = new ArrayList<>();

    // Code statement
    private final double pathVariance;
    // Code statement
    private final double maxDistance;
    // Assigns a value
    private int index = 0;
    // Calls a method
    private final AtomicReference<State> state = new AtomicReference<>(State.CALCULATING);

    // Start of a method/block
    public Point getNext() {
        // Branch: checks a condition
        if (index + 1 >= nodes.size()) return null;
        // Calls a method
        var current = nodes.get(index + 1);
        // Returns a value to the caller
        return new Vec(current.x(), current.y(), current.z());
    // End of a block/expression
    }

    // Start of a method/block
    public void setState(PPath.State newState) {
        // Calls a method
        state.set(newState);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum State {
        // Code statement
        CALCULATING,
        // Code statement
        FOLLOWING,
        // Code statement
        TERMINATING, TERMINATED, COMPUTED, BEST_EFFORT, INVALID
    // End of a block/expression
    }

    // Start of a method/block
    State getState() {
        // Returns a value to the caller
        return state.get();
    // End of a block/expression
    }

    // Start of a method/block
    public List<PNode> getNodes() {
        // Returns a value to the caller
        return nodes;
    // End of a block/expression
    }

    // Start of a method/block
    public PPath(double maxDistance, double pathVariance, Runnable onComplete) {
        // Access to the current/parent object
        this.onComplete = onComplete;
        // Access to the current/parent object
        this.maxDistance = maxDistance;
        // Access to the current/parent object
        this.pathVariance = pathVariance;
    // End of a block/expression
    }

    // Start of a method/block
    void runComplete() {
        // Branch: checks a condition
        if (onComplete != null) onComplete.run();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return nodes.toString();
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable PNode.Type getCurrentType() {
        // Branch: checks a condition
        if (index >= nodes.size()) return null;
        // Calls a method
        var current = nodes.get(index);
        // Returns a value to the caller
        return current.getType();
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable Point getCurrent() {
        // Branch: checks a condition
        if (index >= nodes.size()) return null;
        // Calls a method
        var current = nodes.get(index);
        // Returns a value to the caller
        return new Vec(current.x(), current.y(), current.z());
    // End of a block/expression
    }

    // Start of a method/block
    void next() {
        // Branch: checks a condition
        if (index >= nodes.size()) return;
        // Code statement
        index++;
    // End of a block/expression
    }

    // Start of a method/block
    double maxDistance() {
        // Returns a value to the caller
        return maxDistance;
    // End of a block/expression
    }

    // Start of a method/block
    double pathVariance() {
        // Returns a value to the caller
        return pathVariance;
    // End of a block/expression
    }
// End of a block/expression
}
