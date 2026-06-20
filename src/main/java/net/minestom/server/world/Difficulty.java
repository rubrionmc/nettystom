// Package declaration for this file
package net.minestom.server.world;

/**
 * Those are all the difficulties which can be displayed in the player menu.
 * <p>
 * Sets with {@link net.minestom.server.MinecraftServer#setDifficulty(Difficulty)}.
 */
// Type declaration (class/interface/enum/record)
public enum Difficulty {

    // Calls a method
    PEACEFUL((byte) 0), EASY((byte) 1), NORMAL((byte) 2), HARD((byte) 3);

    // Code statement
    private final byte id;

    // Start of a method/block
    Difficulty(byte id) {
        // Access to the current/parent object
        this.id = id;
    // End of a block/expression
    }

    // Start of a method/block
    public byte getId() {
        // Returns a value to the caller
        return id;
    // End of a block/expression
    }
// End of a block/expression
}
