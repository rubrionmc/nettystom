// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when {@link Player#respawn()} is executed (for custom respawn or as a result of
 * {@link net.minestom.server.network.packet.client.play.ClientStatusPacket}
 */
// Type declaration (class/interface/enum/record)
public class PlayerRespawnEvent implements PlayerInstanceEvent {

    // Code statement
    private final Player player;
    // Code statement
    private Pos respawnPosition;

    // Start of a method/block
    public PlayerRespawnEvent(Player player) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.respawnPosition = player.getRespawnPoint();
    // End of a block/expression
    }

    /**
     * Gets the respawn position.
     * <p>
     * Is by default {@link Player#getRespawnPoint()}
     *
     * @return the respawn position
     */
    // Start of a method/block
    public Pos getRespawnPosition() {
        // Returns a value to the caller
        return respawnPosition;
    // End of a block/expression
    }

    /**
     * Changes the respawn position.
     *
     * @param respawnPosition the new respawn position
     */
    // Start of a method/block
    public void setRespawnPosition(Pos respawnPosition) {
        // Access to the current/parent object
        this.respawnPosition = respawnPosition;
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
