// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.entity.Player;

/**
 * Represents any event called on a {@link Player}.
 */
// Type declaration (class/interface/enum/record)
public interface PlayerEvent extends EntityEvent {

    /**
     * Gets the player.
     *
     * @return the player
     */
    // Calls a method
    Player getPlayer();

    /**
     * Returns {@link #getPlayer()}.
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    default Player getEntity() {
        // Returns a value to the caller
        return getPlayer();
    // End of a block/expression
    }
// End of a block/expression
}
