// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;

/**
 * Called after the player signals the server that his settings has been modified.
 */
// Type declaration (class/interface/enum/record)
public class PlayerSettingsChangeEvent implements PlayerEvent {

    // Code statement
    private final Player player;

    // Start of a method/block
    public PlayerSettingsChangeEvent(Player player) {
        // Access to the current/parent object
        this.player = player;
    // End of a block/expression
    }

    /**
     * Gets the player who changed his settings.
     * <p>
     * You can retrieve the new player settings with {@link Player#getSettings()}.
     *
     * @return the player
     */
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
