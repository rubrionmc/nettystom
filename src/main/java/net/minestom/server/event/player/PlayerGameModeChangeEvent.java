// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when the gamemode of a player is being modified.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerGameModeChangeEvent implements PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private GameMode newGameMode;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerGameModeChangeEvent(Player player, GameMode newGameMode) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.newGameMode = newGameMode;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the target gamemode.
     *
     * @return the target gamemode
     */
    // Début d'une méthode/d'un bloc
    public GameMode getNewGameMode() {
        // Renvoie une valeur à l'appelant
        return newGameMode;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the target gamemode.
     *
     * @param newGameMode the new target gamemode
     */
    // Début d'une méthode/d'un bloc
    public void setNewGameMode(GameMode newGameMode) {
        // Accès à l'objet courant/parent
        this.newGameMode = newGameMode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
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
