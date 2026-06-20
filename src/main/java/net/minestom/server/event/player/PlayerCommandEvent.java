// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called every time a player send a message starting by '/'.
 */
// Type declaration (class/interface/enum/record)
public class PlayerCommandEvent implements PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private String command;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PlayerCommandEvent(Player player, String command) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.command = command;
    // End of a block/expression
    }

    /**
     * Gets the command used (command name + arguments).
     *
     * @return the command that the player wants to execute
     */
    // Start of a method/block
    public String getCommand() {
        // Returns a value to the caller
        return command;
    // End of a block/expression
    }

    /**
     * Changes the command to execute.
     *
     * @param command the new command
     */
    // Start of a method/block
    public void setCommand(String command) {
        // Access to the current/parent object
        this.command = command;
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
