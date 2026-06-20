// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * @deprecated Use {@link PlayerInputEvent} instead.
 */
// Annotation pour l'élément suivant
@Deprecated(forRemoval = true)
// Déclaration de type (classe/interface/enum/record)
public class PlayerStopSneakingEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;

    // Début d'une méthode/d'un bloc
    public PlayerStopSneakingEvent(Player player) {
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
