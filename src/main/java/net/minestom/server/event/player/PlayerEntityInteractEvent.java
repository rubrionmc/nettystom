// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a {@link Player} interacts (right-click) with an {@link Entity}.
 */
// Type declaration (class/interface/enum/record)
public class PlayerEntityInteractEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final Entity entityTarget;
    // Code statement
    private final PlayerHand hand;
    // Code statement
    private final Point interactPosition;

    // Code statement
    public PlayerEntityInteractEvent(Player player, Entity entityTarget, PlayerHand hand,
                                     // Start of a method/block
                                     Point interactPosition) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.entityTarget = entityTarget;
        // Access to the current/parent object
        this.hand = hand;
        // Access to the current/parent object
        this.interactPosition = interactPosition;
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

    /**
     * Gets the {@link Entity} with who {@link #getPlayer()} is interacting.
     *
     * @return the {@link Entity}
     */
    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return entityTarget;
    // End of a block/expression
    }

    /**
     * Gets with which hand the player interacted with the entity.
     *
     * @return the hand
     */
    // Start of a method/block
    public PlayerHand getHand() {
        // Returns a value to the caller
        return hand;
    // End of a block/expression
    }

    /**
     * Gets the position at which the entity was interacted
     *
     * @return the interaction position
     */
    // Start of a method/block
    public Point getInteractPosition() {
        // Returns a value to the caller
        return interactPosition;
    // End of a block/expression
    }
// End of a block/expression
}