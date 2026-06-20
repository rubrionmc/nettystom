// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;

/**
 * Called after the player signals the server that his settings has been modified.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerSettingsChangeEvent implements PlayerEvent {

    // Instruction de code
    private final Player player;

    // Début d'une méthode/d'un bloc
    public PlayerSettingsChangeEvent(Player player) {
        // Accès à l'objet courant/parent
        this.player = player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player who changed his settings.
     * <p>
     * You can retrieve the new player settings with {@link Player#getSettings()}.
     *
     * @return the player
     */
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
