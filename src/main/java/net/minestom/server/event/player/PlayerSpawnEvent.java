// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called when a new instance is set for a player.
 */
// Type declaration (class/interface/enum/record)
public class PlayerSpawnEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Instance spawnInstance;
    // Code statement
    private final boolean firstSpawn;

    // Start of a method/block
    public PlayerSpawnEvent(Player player, Instance spawnInstance, boolean firstSpawn) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.spawnInstance = spawnInstance;
        // Access to the current/parent object
        this.firstSpawn = firstSpawn;
    // End of a block/expression
    }

    /**
     * Gets the player's new instance.
     *
     * @return the instance
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Instance getSpawnInstance() {
        // Returns a value to the caller
        return spawnInstance;
    // End of a block/expression
    }

    /**
     * 'true' if the player is spawning for the first time. 'false' if this spawn event was triggered by a dimension teleport
     *
     * @return true if this is the first spawn, false otherwise
     */
    // Start of a method/block
    public boolean isFirstSpawn() {
        // Returns a value to the caller
        return firstSpawn;
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
