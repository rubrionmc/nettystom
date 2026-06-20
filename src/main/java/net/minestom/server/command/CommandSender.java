// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.kyori.adventure.identity.Identified;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.tag.Taggable;

/**
 * Represents something which can send commands to the server.
 * <p>
 * Main implementations are {@link Player} and {@link ConsoleSender}.
 */
// Type declaration (class/interface/enum/record)
public interface CommandSender extends Audience, Taggable, Identified {

    /**
     * Sends a raw string message.
     *
     * @param message the message to send
     */
    // Start of a method/block
    default void sendMessage(String message) {
        // Access to the current/parent object
        this.sendMessage(Component.text(message));
    // End of a block/expression
    }

    /**
     * Sends multiple raw string messages.
     *
     * @param messages the messages to send
     */
    // Start of a method/block
    default void sendMessage(String [] messages) {
        // Loop: repeats a block
        for (String message : messages) {
            // Calls a method
            sendMessage(message);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
