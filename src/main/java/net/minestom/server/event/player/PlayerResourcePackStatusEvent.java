// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.kyori.adventure.resource.ResourcePackStatus;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerEvent;

// Import of a required class
import java.util.UUID;

/**
 * Called when a player warns the server of a resource pack status.
 */
// Type declaration (class/interface/enum/record)
public class PlayerResourcePackStatusEvent implements PlayerEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final ResourcePackStatus status;
    // Code statement
    private final UUID packUUID;

    // Start of a method/block
    public PlayerResourcePackStatusEvent(Player player, UUID packUUID, ResourcePackStatus status) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.status = status;
        // Access to the current/parent object
        this.packUUID = packUUID;
    // End of a block/expression
    }

    /**
     * Gets the resource pack status.
     *
     * @return the resource pack status
     */
    // Start of a method/block
    public ResourcePackStatus getStatus() {
        // Returns a value to the caller
        return status;
    // End of a block/expression
    }

    /**
     * Gets the associated pack UUID that has resolved on the client with the particular status
     * @return the UUID of the resource pack
     */
    // Start of a method/block
    public UUID getPackUuid() {
        // Returns a value to the caller
        return packUUID;
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
