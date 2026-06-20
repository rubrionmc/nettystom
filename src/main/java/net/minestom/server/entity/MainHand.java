// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;

/**
 * Represents where is located the main hand of the player (can be changed in Minecraft option).
 */
// Type declaration (class/interface/enum/record)
public enum MainHand {
    // Code statement
    LEFT,
    // Code statement
    RIGHT;

    // Assigns a value
    public static final NetworkBuffer.Type<MainHand> NETWORK_TYPE = NetworkBuffer.Enum(
        // Code statement
        MainHand.class);
// End of a block/expression
}
