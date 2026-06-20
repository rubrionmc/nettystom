// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player tries to pick an entity (middle-click).
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerPickEntityEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;

    // Instruction de code
    private final Entity entityTarget;
    // Instruction de code
    private final boolean includeData;

    // Instruction de code
    public PlayerPickEntityEvent(Player player, @Nullable Entity entityTarget,
                                 // Début d'une méthode/d'un bloc
                                 boolean includeData) {
        // Accès à l'objet courant/parent
        this.player = player;

        // Accès à l'objet courant/parent
        this.entityTarget = entityTarget;
        // Accès à l'objet courant/parent
        this.includeData = includeData;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity which was picked. May be null if the entity is not known by the server (eg spawned with packets).
     *
     * @return the entity which was picked
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return entityTarget;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Get if the entity data should be included in the result (control middle-click).
     *
     * @return if the entity data should be included.
     */
    // Début d'une méthode/d'un bloc
    public boolean isIncludeData() {
        // Renvoie une valeur à l'appelant
        return this.includeData;
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
