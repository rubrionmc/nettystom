// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player uses the F3+F4 menu to try and change their gamemode.
 */
// Type declaration (class/interface/enum/record)
public class PlayerGameModeRequestEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final GameMode requestedGameMode;

    // Start of a method/block
    public PlayerGameModeRequestEvent(Player player, GameMode requestedGameMode) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.requestedGameMode = requestedGameMode;
    // End of a block/expression
    }

    /**
     * Gets the requested gamemode.
     *
     * @return the requested gamemode
     */
    // Start of a method/block
    public GameMode getRequestedGameMode() {
        // Returns a value to the caller
        return requestedGameMode;
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
