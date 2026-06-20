// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;

/**
 * Used when you want to provide your own player object instead of using the default one.
 * <p>
 * Sets with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface PlayerProvider {

    /**
     * Creates a new {@link Player} object based on his connection data.
     * <p>
     * Called once a client want to join the server and need to have an assigned player object.
     *
     * @param connection  the player connection
     * @param gameProfile the player game profile
     * @return a newly create {@link Player} object
     */
    // Calls a method
    Player createPlayer(PlayerConnection connection, GameProfile gameProfile);
// End of a block/expression
}
