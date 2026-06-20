// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;

/**
 * Used when you want to provide your own player object instead of using the default one.
 * <p>
 * Sets with {@link ConnectionManager#setPlayerProvider(PlayerProvider)}.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
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
    // Appelle une méthode
    Player createPlayer(PlayerConnection connection, GameProfile gameProfile);
// Fin d'un bloc/d'une expression
}
