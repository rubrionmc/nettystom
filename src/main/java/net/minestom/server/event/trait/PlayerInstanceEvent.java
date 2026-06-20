// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.entity.Player;

/**
 * Represents an {@link PlayerEvent} which happen in {@link Player#getInstance()}.
 * Useful if you need to listen to player events happening in its instance.
 * <p>
 * Be aware that the player's instance must be non-null.
 */
// Type declaration (class/interface/enum/record)
public interface PlayerInstanceEvent extends PlayerEvent, EntityInstanceEvent {
// End of a block/expression
}
