// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player indicates that they have finished loading into the world.
 *
 * <p>This is driven by the client so should be considered as such.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerLoadedEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;

    // Début d'une méthode/d'un bloc
    public PlayerLoadedEvent(Player player) {
        // Accès à l'objet courant/parent
        this.player = player;
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
