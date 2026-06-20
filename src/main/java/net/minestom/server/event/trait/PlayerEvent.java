// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

/**
 * Represents any event called on a {@link Player}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface PlayerEvent extends EntityEvent {

    /**
     * Gets the player.
     *
     * @return the player
     */
    // Appelle une méthode
    Player getPlayer();

    /**
     * Returns {@link #getPlayer()}.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Player getEntity() {
        // Renvoie une valeur à l'appelant
        return getPlayer();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
