// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

/**
 * Called when a {@link Player} interacts (right-click) with an {@link Entity}.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerEntityInteractEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Entity entityTarget;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final Point interactPosition;

    // Instruction de code
    public PlayerEntityInteractEvent(Player player, Entity entityTarget, PlayerHand hand,
                                     // Début d'une méthode/d'un bloc
                                     Point interactPosition) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.entityTarget = entityTarget;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.interactPosition = interactPosition;
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

    /**
     * Gets the {@link Entity} with who {@link #getPlayer()} is interacting.
     *
     * @return the {@link Entity}
     */
    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return entityTarget;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets with which hand the player interacted with the entity.
     *
     * @return the hand
     */
    // Début d'une méthode/d'un bloc
    public PlayerHand getHand() {
        // Renvoie une valeur à l'appelant
        return hand;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the position at which the entity was interacted
     *
     * @return the interaction position
     */
    // Début d'une méthode/d'un bloc
    public Point getInteractPosition() {
        // Renvoie une valeur à l'appelant
        return interactPosition;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}