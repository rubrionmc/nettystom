// Déclaration du paquet de ce fichier
package net.minestom.server.event.trait;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

/**
 * Represents an {@link PlayerEvent} which happen in {@link Player#getInstance()}.
 * Useful if you need to listen to player events happening in its instance.
 * <p>
 * Be aware that the player's instance must be non-null.
 */
// Déclaration de type (classe/interface/enum/record)
public interface PlayerInstanceEvent extends PlayerEvent, EntityInstanceEvent {
// Fin d'un bloc/d'une expression
}
