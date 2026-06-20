// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;

/**
 * Called every time a {@link Player} writes and sends something in the chat.
 * The event can be cancelled to not send anything, and the final message can be changed.
 */
// Type declaration (class/interface/enum/record)
public class PlayerChatEvent implements PlayerInstanceEvent, CancellableEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Collection<Player> recipients;
    // Code statement
    private final String rawMessage;
    // Code statement
    private Component formattedMessage;
    // Code statement
    private boolean cancelled;

    // Code statement
    public PlayerChatEvent(Player player, Collection<Player> recipients,
                           // Start of a method/block
                           String rawMessage) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.recipients = new ArrayList<>(recipients);
        // Access to the current/parent object
        this.rawMessage = rawMessage;
        // Calls a method
        formattedMessage = buildDefaultChatMessage();
    // End of a block/expression
    }

    /**
     * Returns the players who will receive the message.
     * <p>
     * It can be modified to add and remove recipients.
     *
     * @return a modifiable list of the message's targets
     */
    // Start of a method/block
    public Collection<Player> getRecipients() {
        // Returns a value to the caller
        return recipients;
    // End of a block/expression
    }

    /**
     * Gets the original message content sent by the player.
     *
     * @return the sender's message
     */
    // Start of a method/block
    public String getRawMessage() {
        // Returns a value to the caller
        return rawMessage;
    // End of a block/expression
    }

    /**
     * Gets the final message component that will be sent.
     *
     * @return the chat message component
     */
    // Start of a method/block
    public Component getFormattedMessage() {
        // Returns a value to the caller
        return formattedMessage;
    // End of a block/expression
    }

    /**
     * Used to change the final message component.
     *
     * @param message the new message component
     */
    // Start of a method/block
    public void setFormattedMessage(Component message) {
        // Assigns a value
        formattedMessage = message;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
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

    // Start of a method/block
    private Component buildDefaultChatMessage() {
        // Returns a value to the caller
        return Component.translatable("chat.type.text")
                // Code statement
                .arguments(
                        // Code statement
                        Component.text(player.getUsername())
                                // Code statement
                                .insertion(player.getUsername())
                                // Code statement
                                .hoverEvent(player),
                        // Calls a method
                        Component.text(rawMessage));
    // End of a block/expression
    }
// End of a block/expression
}
