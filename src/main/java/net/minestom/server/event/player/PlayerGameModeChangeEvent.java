// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when the gamemode of a player is being modified.
 */
// Type declaration (class/interface/enum/record)
public class PlayerGameModeChangeEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private GameMode newGameMode;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerGameModeChangeEvent(Player player, GameMode newGameMode) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.newGameMode = newGameMode;
    // End of a block/expression
    }

    /**
     * Gets the target gamemode.
     *
     * @return the target gamemode
     */
    // Start of a method/block
    public GameMode getNewGameMode() {
        // Returns a value to the caller
        return newGameMode;
    // End of a block/expression
    }

    /**
     * Changes the target gamemode.
     *
     * @param newGameMode the new target gamemode
     */
    // Start of a method/block
    public void setNewGameMode(GameMode newGameMode) {
        // Access to the current/parent object
        this.newGameMode = newGameMode;
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
