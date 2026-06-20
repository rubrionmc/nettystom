// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player receive a new chunk data.
 */
// Type declaration (class/interface/enum/record)
public class PlayerChunkLoadEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final int chunkX, chunkZ;

    // Start of a method/block
    public PlayerChunkLoadEvent(Player player, int chunkX, int chunkZ) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.chunkX = chunkX;
        // Access to the current/parent object
        this.chunkZ = chunkZ;
    // End of a block/expression
    }

    /**
     * Gets the chunk X.
     *
     * @return the chunk X
     */
    // Start of a method/block
    public int getChunkX() {
        // Returns a value to the caller
        return chunkX;
    // End of a block/expression
    }

    /**
     * Gets the chunk Z.
     *
     * @return the chunk Z
     */
    // Start of a method/block
    public int getChunkZ() {
        // Returns a value to the caller
        return chunkZ;
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
