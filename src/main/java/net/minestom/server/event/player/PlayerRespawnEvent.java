// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when {@link Player#respawn()} is executed (for custom respawn or as a result of
 * {@link net.minestom.server.network.packet.client.play.ClientStatusPacket}
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerRespawnEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private Pos respawnPosition;

    // Début d'une méthode/d'un bloc
    public PlayerRespawnEvent(Player player) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.respawnPosition = player.getRespawnPoint();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the respawn position.
     * <p>
     * Is by default {@link Player#getRespawnPoint()}
     *
     * @return the respawn position
     */
    // Début d'une méthode/d'un bloc
    public Pos getRespawnPosition() {
        // Renvoie une valeur à l'appelant
        return respawnPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the respawn position.
     *
     * @param respawnPosition the new respawn position
     */
    // Début d'une méthode/d'un bloc
    public void setRespawnPosition(Pos respawnPosition) {
        // Accès à l'objet courant/parent
        this.respawnPosition = respawnPosition;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
