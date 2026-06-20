// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;

/**
 * Represents the game mode of a player.
 * <p>
 * Can be set with {@link Player#setGameMode(GameMode)}.
 */
// Type declaration (class/interface/enum/record)
public enum GameMode {
    // Code statement
    SURVIVAL(false, false, false),
    // Code statement
    CREATIVE(true, true, true),
    // Code statement
    ADVENTURE(false, false, false),
    // Calls a method
    SPECTATOR(true, true, false);

    // Code statement
    private final boolean allowFlying;
    // Code statement
    private final boolean invulnerable;
    // Code statement
    private final boolean instantBreak;

    // Start of a method/block
    GameMode(boolean allowFlying, boolean invulnerable, boolean instantBreak) {
        // Access to the current/parent object
        this.allowFlying = allowFlying;
        // Access to the current/parent object
        this.invulnerable = invulnerable;
        // Access to the current/parent object
        this.instantBreak = instantBreak;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean allowFlying() {
        // Returns a value to the caller
        return allowFlying;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean invulnerable() {
        // Returns a value to the caller
        return invulnerable;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean instantBreak() {
        // Returns a value to the caller
        return instantBreak;
    // End of a block/expression
    }

    // Calls a method
    private static final GameMode[] VALUES = values();

    // Assigns a value
    public static final NetworkBuffer.Type<GameMode> NETWORK_TYPE = BYTE.transform(
            // Code statement
            id -> VALUES[id],
            // Code statement
            gameMode -> (byte) gameMode.ordinal()
    // End of a block/expression
    );

    // Assigns a value
    public static final NetworkBuffer.Type<GameMode> OPT_NETWORK_TYPE = BYTE.transform(
            // Code statement
            id -> id != -1 ? VALUES[id] : null,
            // Code statement
            gameMode -> gameMode != null ? (byte) gameMode.ordinal() : -1
    // End of a block/expression
    );
// End of a block/expression
}
