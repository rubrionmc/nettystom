// Package declaration for this file
package net.minestom.server.message;

// Import of a required class
import java.util.EnumSet;

/**
 * The messages that a player is willing to receive.
 */
// Type declaration (class/interface/enum/record)
public enum ChatMessageType {
    /**
     * The client wants all chat messages.
     */
    // Code statement
    FULL(EnumSet.allOf(ChatPosition.class)),

    /**
     * The client only wants messages from commands, or system messages.
     */
    // Code statement
    SYSTEM(EnumSet.of(ChatPosition.SYSTEM_MESSAGE, ChatPosition.GAME_INFO)),

    /**
     * The client doesn't want any messages.
     */
    // Calls a method
    NONE(EnumSet.of(ChatPosition.GAME_INFO));

    // Code statement
    private final EnumSet<ChatPosition> acceptedPositions;

    // Start of a method/block
    ChatMessageType(EnumSet<ChatPosition> acceptedPositions) {
        // Access to the current/parent object
        this.acceptedPositions = acceptedPositions;
    // End of a block/expression
    }

    /**
     * Checks if this message type is accepting of messages from a given position.
     *
     * @param chatPosition the position
     * @return if the message is accepted
     */
    // Start of a method/block
    public boolean accepts(ChatPosition chatPosition) {
        // Returns a value to the caller
        return this.acceptedPositions.contains(chatPosition);
    // End of a block/expression
    }
// End of a block/expression
}
