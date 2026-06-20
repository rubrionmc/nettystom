// Package declaration for this file
package net.minestom.server.entity.pathfinding;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class PNode {
    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        WALK,
        // Code statement
        JUMP,
        // Code statement
        FALL,
        // Code statement
        CLIMB,
        // Code statement
        CLIMB_WALL,
        // Code statement
        SWIM,
        // Code statement
        FLY, REPATH
    // End of a block/expression
    }

    // Code statement
    private double g;
    // Code statement
    private double h;
    // Code statement
    private PNode parent;
    // Code statement
    private double pointX;
    // Code statement
    private double pointY;
    // Code statement
    private double pointZ;
    // Code statement
    private int hashCode;

    // Code statement
    private Type type;

    // Start of a method/block
    public PNode(double px, double py, double pz, double g, double h, @Nullable PNode parent) {
        // Calls a method
        this(px, py, pz, g, h, Type.WALK, parent);
    // End of a block/expression
    }

    // Start of a method/block
    public PNode(double px, double py, double pz, double g, double h, PNode.Type type, @Nullable PNode parent) {
        // Access to the current/parent object
        this.g = g;
        // Access to the current/parent object
        this.h = h;
        // Access to the current/parent object
        this.parent = parent;
        // Access to the current/parent object
        this.type = type;
        // Access to the current/parent object
        this.pointX = px;
        // Access to the current/parent object
        this.pointY = py;
        // Access to the current/parent object
        this.pointZ = pz;
        // Access to the current/parent object
        this.hashCode = cantor((int) Math.floor(px), cantor((int) Math.floor(py), (int) Math.floor(pz)));
    // End of a block/expression
    }

    // Start of a method/block
    public PNode(Point point, double g, double h, Type walk, @Nullable PNode parent) {
        // Calls a method
        this(point.x(), point.y(), point.z(), g, h, walk, parent);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return hashCode;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object obj) {
        // Branch: checks a condition
        if (obj == null) return false;
        // Branch: checks a condition
        if (obj == this) return true;
        // Branch: checks a condition
        if (!(obj instanceof PNode other)) return false;
        // Returns a value to the caller
        return this.hashCode == other.hashCode;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return "PNode{" +
                // Code statement
                "point=" + pointX + ", " + pointY + ", " + pointZ +
                // Code statement
                ", d=" + (g + h) +
                // Code statement
                ", type=" + type +
                // Code statement
                '}';
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public double x() {
        // Returns a value to the caller
        return pointX;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public double y() {
        // Returns a value to the caller
        return pointY;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public double z() {
        // Returns a value to the caller
        return pointZ;
    // End of a block/expression
    }

    // Start of a method/block
    public int blockX() {
        // Returns a value to the caller
        return (int) Math.floor(pointX);
    // End of a block/expression
    }

    // Start of a method/block
    public int blockY() {
        // Returns a value to the caller
        return (int) Math.floor(pointY);
    // End of a block/expression
    }

    // Start of a method/block
    public int blockZ() {
        // Returns a value to the caller
        return (int) Math.floor(pointZ);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public Type getType() {
        // Returns a value to the caller
        return type;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public double g() {
        // Returns a value to the caller
        return g;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public double h() {
        // Returns a value to the caller
        return h;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setG(double v) {
        // Access to the current/parent object
        this.g = v;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setH(double heuristic) {
        // Access to the current/parent object
        this.h = heuristic;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setType(PNode.Type newType) {
        // Access to the current/parent object
        this.type = newType;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setPoint(double px, double py, double pz) {
        // Access to the current/parent object
        this.pointX = px;
        // Access to the current/parent object
        this.pointY = py;
        // Access to the current/parent object
        this.pointZ = pz;
        // Access to the current/parent object
        this.hashCode = cantor((int) Math.floor(px), cantor((int) Math.floor(py), (int) Math.floor(pz)));
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public @Nullable PNode parent() {
        // Returns a value to the caller
        return parent;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void setParent(@Nullable PNode current) {
        // Access to the current/parent object
        this.parent = current;
    // End of a block/expression
    }

    // Start of a method/block
    private static int cantor(int a, int b) {
        // Assigns a value
        int ca = a >= 0 ? 2 * a : -2 * a - 1;
        // Assigns a value
        int cb = b >= 0 ? 2 * b : -2 * b - 1;
        // Returns a value to the caller
        return (ca + cb + 1) * (ca + cb) / 2 + cb;
    // End of a block/expression
    }
// End of a block/expression
}
