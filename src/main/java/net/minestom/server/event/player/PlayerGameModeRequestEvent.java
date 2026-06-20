// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player uses the F3+F4 menu to try and change their gamemode.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerGameModeRequestEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final GameMode requestedGameMode;

    // Début d'une méthode/d'un bloc
    public PlayerGameModeRequestEvent(Player player, GameMode requestedGameMode) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.requestedGameMode = requestedGameMode;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the requested gamemode.
     *
     * @return the requested gamemode
     */
    // Début d'une méthode/d'un bloc
    public GameMode getRequestedGameMode() {
        // Renvoie une valeur à l'appelant
        return requestedGameMode;
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
