// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackStatus;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;

// Import d'une classe nécessaire
import java.util.UUID;

/**
 * Called when a player warns the server of a resource pack status.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerResourcePackStatusEvent implements PlayerEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final ResourcePackStatus status;
    // Instruction de code
    private final UUID packUUID;

    // Début d'une méthode/d'un bloc
    public PlayerResourcePackStatusEvent(Player player, UUID packUUID, ResourcePackStatus status) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.status = status;
        // Accès à l'objet courant/parent
        this.packUUID = packUUID;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the resource pack status.
     *
     * @return the resource pack status
     */
    // Début d'une méthode/d'un bloc
    public ResourcePackStatus getStatus() {
        // Renvoie une valeur à l'appelant
        return status;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the associated pack UUID that has resolved on the client with the particular status
     * @return the UUID of the resource pack
     */
    // Début d'une méthode/d'un bloc
    public UUID getPackUuid() {
        // Renvoie une valeur à l'appelant
        return packUUID;
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
