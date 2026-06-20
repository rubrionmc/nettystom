// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when the player swings his hand.
 */
// Type declaration (class/interface/enum/record)
public class PlayerHandAnimationEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final PlayerHand hand;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerHandAnimationEvent(Player player, PlayerHand hand) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.hand = hand;
    // End of a block/expression
    }

    /**
     * Gets the hand used.
     *
     * @return the hand
     */
    // Start of a method/block
    public PlayerHand getHand() {
        // Returns a value to the caller
        return hand;
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
// End of a block/expression
}
