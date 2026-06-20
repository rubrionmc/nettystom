// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player start flying.
 */
// Type declaration (class/interface/enum/record)
public class PlayerStartFlyingEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;

    // Start of a method/block
    public PlayerStartFlyingEvent(Player player) {
        // Access to the current/parent object
        this.player = player;
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
