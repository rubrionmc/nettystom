// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.minestom.server.utils.Direction;

// Type declaration (class/interface/enum/record)
public enum BlockFace {
    // Code statement
    BOTTOM(Direction.DOWN),
    // Code statement
    TOP(Direction.UP),
    // Code statement
    NORTH(Direction.NORTH),
    // Code statement
    SOUTH(Direction.SOUTH),
    // Code statement
    WEST(Direction.WEST),
    // Calls a method
    EAST(Direction.EAST);

    // Code statement
    private final Direction direction;

    // Start of a method/block
    BlockFace(Direction direction) {
        // Access to the current/parent object
        this.direction = direction;
    // End of a block/expression
    }

    // Start of a method/block
    public Direction toDirection() {
        // Returns a value to the caller
        return direction;
    // End of a block/expression
    }

    // Start of a method/block
    public BlockFace getOppositeFace() {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case BOTTOM -> TOP;
            // Multiple branching (switch/case)
            case TOP -> BOTTOM;
            // Multiple branching (switch/case)
            case NORTH -> SOUTH;
            // Multiple branching (switch/case)
            case SOUTH -> NORTH;
            // Multiple branching (switch/case)
            case WEST -> EAST;
            // Multiple branching (switch/case)
            case EAST -> WEST;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSimilar(BlockFace other) {
        // Returns a value to the caller
        return this == other || this == other.getOppositeFace();
    // End of a block/expression
    }

    /**
     * Gets the horizontal BlockFace from the given yaw angle
     *
     * @param yaw the yaw angle
     * @return a horizontal BlockFace
     */
    // Start of a method/block
    public static BlockFace fromYaw(float yaw) {
        // Calls a method
        float degrees = (yaw - 90) % 360;
        // Branch: checks a condition
        if (degrees < 0) {
            // Code statement
            degrees += 360;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (0 <= degrees && degrees < 45) {
            // Returns a value to the caller
            return BlockFace.WEST;
        // Branch: checks a condition
        } else if (45 <= degrees && degrees < 135) {
            // Returns a value to the caller
            return BlockFace.NORTH;
        // Branch: checks a condition
        } else if (135 <= degrees && degrees < 225) {
            // Returns a value to the caller
            return BlockFace.EAST;
        // Branch: checks a condition
        } else if (225 <= degrees && degrees < 315) {
            // Returns a value to the caller
            return BlockFace.SOUTH;
        // Alternative branch of the condition
        } else { // 315 <= degrees && degrees < 360
            // Returns a value to the caller
            return BlockFace.WEST;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Get the BlockFace corresponding to the given {@link Direction}.
     *
     * @param direction the direction
     * @return the corresponding BlockFace
     */
    // Start of a method/block
    public static BlockFace fromDirection(Direction direction) {
        // Returns a value to the caller
        return switch (direction) {
            // Multiple branching (switch/case)
            case UP -> TOP;
            // Multiple branching (switch/case)
            case DOWN -> BOTTOM;
            // Multiple branching (switch/case)
            case NORTH -> NORTH;
            // Multiple branching (switch/case)
            case SOUTH -> SOUTH;
            // Multiple branching (switch/case)
            case WEST -> WEST;
            // Multiple branching (switch/case)
            case EAST -> EAST;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
