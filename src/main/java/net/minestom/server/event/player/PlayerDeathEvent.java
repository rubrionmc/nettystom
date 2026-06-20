// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player die in {@link Player#kill()}.
 */
// Type declaration (class/interface/enum/record)
public class PlayerDeathEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private Component deathText;
    // Code statement
    private Component chatMessage;

    // Start of a method/block
    public PlayerDeathEvent(Player player, Component deathText, Component chatMessage) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.deathText = deathText;
        // Access to the current/parent object
        this.chatMessage = chatMessage;
    // End of a block/expression
    }

    /**
     * Gets the text displayed in the death screen.
     *
     * @return the death text, can be null
     */
    // Start of a method/block
    public @Nullable Component getDeathText() {
        // Returns a value to the caller
        return deathText;
    // End of a block/expression
    }

    /**
     * Changes the text displayed in the death screen.
     *
     * @param deathText the death text to display, null to remove
     */
    // Start of a method/block
    public void setDeathText(@Nullable Component deathText) {
        // Access to the current/parent object
        this.deathText = deathText;
    // End of a block/expression
    }

    /**
     * Gets the message sent to chat.
     *
     * @return the death chat message
     */
    // Start of a method/block
    public @Nullable Component getChatMessage() {
        // Returns a value to the caller
        return chatMessage;
    // End of a block/expression
    }

    /**
     * Changes the text sent in chat
     *
     * @param chatMessage the death message to send, null to remove
     */
    // Start of a method/block
    public void setChatMessage(@Nullable Component chatMessage) {
        // Access to the current/parent object
        this.chatMessage = chatMessage;
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
