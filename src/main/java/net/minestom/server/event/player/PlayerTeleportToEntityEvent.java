// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Import d'une classe nécessaire
import java.util.Objects;

/// Called when a player teleports to another entity, through the spectator hotbar.
///
/// The target is not required to be in the same instance as the player.
// Déclaration de type (classe/interface/enum/record)
public class PlayerTeleportToEntityEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Entity target;

    // Début d'une méthode/d'un bloc
    public PlayerTeleportToEntityEvent(Player player, Entity target) {
        // Accès à l'objet courant/parent
        this.player = Objects.requireNonNull(player, "player");
        // Accès à l'objet courant/parent
        this.target = Objects.requireNonNull(target, "target");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return target;
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
