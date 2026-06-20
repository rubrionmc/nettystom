// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;

/**
 * Called when a player send {@link ClientPluginMessagePacket}.
 */
// Type declaration (class/interface/enum/record)
public class PlayerPluginMessageEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final String identifier;
    // Code statement
    private final byte[] message;

    // Start of a method/block
    public PlayerPluginMessageEvent(Player player, String identifier, byte[] message) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.identifier = identifier;
        // Access to the current/parent object
        this.message = message;
    // End of a block/expression
    }

    /**
     * Gets the message identifier.
     *
     * @return the identifier
     */
    // Start of a method/block
    public String getIdentifier() {
        // Returns a value to the caller
        return identifier;
    // End of a block/expression
    }

    /**
     * Gets the message data as a byte array.
     *
     * @return the message
     */
    // Start of a method/block
    public byte[] getMessage() {
        // Returns a value to the caller
        return message;
    // End of a block/expression
    }

    /**
     * Gets the message data as a String.
     *
     * @return the message
     */
    // Start of a method/block
    public String getMessageString() {
        // Returns a value to the caller
        return new String(message);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
