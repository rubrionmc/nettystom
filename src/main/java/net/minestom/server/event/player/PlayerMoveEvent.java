// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player is modifying his position.
 */
// Type declaration (class/interface/enum/record)
public class PlayerMoveEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private Pos newPosition;
    // Code statement
    private final boolean onGround;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerMoveEvent(Player player, Pos newPosition, boolean onGround) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.newPosition = newPosition;
        // Access to the current/parent object
        this.onGround = onGround;
    // End of a block/expression
    }

    /**
     * Gets the target position.
     *
     * @return the new position
     */
    // Start of a method/block
    public Pos getNewPosition() {
        // Returns a value to the caller
        return newPosition;
    // End of a block/expression
    }

    /**
     * Changes the target position.
     *
     * @param newPosition the new target position
     */
    // Start of a method/block
    public void setNewPosition(Pos newPosition) {
        // Access to the current/parent object
        this.newPosition = newPosition;
    // End of a block/expression
    }

    /**
     * Gets if the player is now on the ground.
     * This is the original value that the client sent,
     * and is not modified by setting the new position.
     *
     * @return onGround
     */
    // Start of a method/block
    public boolean isOnGround() {
        // Returns a value to the caller
        return onGround;
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
