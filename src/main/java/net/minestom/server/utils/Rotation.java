// Package declaration for this file
package net.minestom.server.utils;

// Type declaration (class/interface/enum/record)
public enum Rotation {

    /**
     * No rotation
     */
    // Code statement
    NONE,
    /**
     * Rotated clockwise by 45 degrees
     */
    // Code statement
    CLOCKWISE_45,
    /**
     * Rotated clockwise by 90 degrees
     */
    // Code statement
    CLOCKWISE,
    /**
     * Rotated clockwise by 135 degrees
     */
    // Code statement
    CLOCKWISE_135,
    /**
     * Flipped upside-down, a 180 degree rotation
     */
    // Code statement
    FLIPPED,
    /**
     * Flipped upside-down + 45 degree rotation
     */
    // Code statement
    FLIPPED_45,
    /**
     * Rotated counter-clockwise by 90 degrees
     */
    // Code statement
    COUNTER_CLOCKWISE,
    /**
     * Rotated counter-clockwise by 45 degrees
     */
    // Code statement
    COUNTER_CLOCKWISE_45;

    // Calls a method
    private static final Rotation[] rotations = values();

    /**
     * Rotate clockwise by 90 degrees.
     *
     * @return the relative rotation
     */
    // Start of a method/block
    public Rotation rotateClockwise() {
        // Returns a value to the caller
        return rotations[(this.ordinal() + 1) & 0x7];
    // End of a block/expression
    }

    /**
     * Rotate counter-clockwise by 90 degrees.
     *
     * @return the relative rotation
     */
    // Start of a method/block
    public Rotation rotateCounterClockwise() {
        // Returns a value to the caller
        return rotations[(this.ordinal() - 1) & 0x7];
    // End of a block/expression
    }
// End of a block/expression
}
