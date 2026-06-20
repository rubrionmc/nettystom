// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a player is modifying his position.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerMoveEvent implements PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private Pos newPosition;
    // Instruction de code
    private final boolean onGround;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerMoveEvent(Player player, Pos newPosition, boolean onGround) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.newPosition = newPosition;
        // Accès à l'objet courant/parent
        this.onGround = onGround;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the target position.
     *
     * @return the new position
     */
    // Début d'une méthode/d'un bloc
    public Pos getNewPosition() {
        // Renvoie une valeur à l'appelant
        return newPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the target position.
     *
     * @param newPosition the new target position
     */
    // Début d'une méthode/d'un bloc
    public void setNewPosition(Pos newPosition) {
        // Accès à l'objet courant/parent
        this.newPosition = newPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the player is now on the ground.
     * This is the original value that the client sent,
     * and is not modified by setting the new position.
     *
     * @return onGround
     */
    // Début d'une méthode/d'un bloc
    public boolean isOnGround() {
        // Renvoie une valeur à l'appelant
        return onGround;
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
