// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player indicates that they have finished loading into the world.
 *
 * <p>This is driven by the client so should be considered as such.</p>
 */
// Type declaration (class/interface/enum/record)
public class PlayerLoadedEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;

    // Start of a method/block
    public PlayerLoadedEvent(Player player) {
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
