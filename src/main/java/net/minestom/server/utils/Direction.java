// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.coordinate.Vec;

// Type declaration (class/interface/enum/record)
public enum Direction {
    // Code statement
    DOWN(0, -1, 0),
    // Code statement
    UP(0, 1, 0),
    // Code statement
    NORTH(0, 0, -1),
    // Code statement
    SOUTH(0, 0, 1),
    // Code statement
    WEST(-1, 0, 0),
    // Calls a method
    EAST(1, 0, 0);

    // Assigns a value
    public static final Direction[] HORIZONTAL = {SOUTH, WEST, NORTH, EAST};

    // Code statement
    private final int normalX;
    // Code statement
    private final int normalY;
    // Code statement
    private final int normalZ;
    // Code statement
    private final Vec normalVec;

    // Start of a method/block
    Direction(int normalX, int normalY, int normalZ) {
        // Access to the current/parent object
        this.normalX = normalX;
        // Access to the current/parent object
        this.normalY = normalY;
        // Access to the current/parent object
        this.normalZ = normalZ;
        // Access to the current/parent object
        this.normalVec = new Vec(normalX, normalY, normalZ);
    // End of a block/expression
    }

    // Start of a method/block
    public int normalX() {
        // Returns a value to the caller
        return normalX;
    // End of a block/expression
    }

    // Start of a method/block
    public int normalY() {
        // Returns a value to the caller
        return normalY;
    // End of a block/expression
    }

    // Start of a method/block
    public int normalZ() {
        // Returns a value to the caller
        return normalZ;
    // End of a block/expression
    }

    // Start of a method/block
    public Vec vec() {
        // Returns a value to the caller
        return normalVec;
    // End of a block/expression
    }

    // Start of a method/block
    public Vec mul(double mult) {
        // Returns a value to the caller
        return normalVec.mul(mult);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean positive() {
        // Returns a value to the caller
        return normalX > 0 || normalY > 0 || normalZ > 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean negative() {
        // Returns a value to the caller
        return !positive();
    // End of a block/expression
    }

    // Start of a method/block
    public boolean vertical() {
        // Returns a value to the caller
        return this == UP || this == DOWN;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean horizontal() {
        // Returns a value to the caller
        return !vertical();
    // End of a block/expression
    }

    // Start of a method/block
    public Direction opposite() {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case UP -> DOWN;
            // Multiple branching (switch/case)
            case DOWN -> UP;
            // Multiple branching (switch/case)
            case EAST -> WEST;
            // Multiple branching (switch/case)
            case WEST -> EAST;
            // Multiple branching (switch/case)
            case NORTH -> SOUTH;
            // Multiple branching (switch/case)
            case SOUTH -> NORTH;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
