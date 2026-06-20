// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;

/**
 * Represents the main or off hand of the player.
 */
// Type declaration (class/interface/enum/record)
public enum PlayerHand {
    // Code statement
    MAIN,
    // Code statement
    OFF;

    // Calls a method
    public static final NetworkBuffer.Type<PlayerHand> NETWORK_TYPE = NetworkBuffer.Enum(PlayerHand.class);
// End of a block/expression
}
