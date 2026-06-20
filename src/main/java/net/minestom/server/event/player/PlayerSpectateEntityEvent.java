// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Import of a required class
import java.util.Objects;

/// Called when a Player tries to spectate an entity (start looking through its eyes).
///
/// Only called when the Player is in the spectator gamemode and if the target is reachable from the current instance.
// Annotation for the following element
@SuppressWarnings("ClassCanBeRecord")
// Type declaration (class/interface/enum/record)
public class PlayerSpectateEntityEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Entity target;

    // Start of a method/block
    public PlayerSpectateEntityEvent(Player player, Entity target) {
        // Access to the current/parent object
        this.player = Objects.requireNonNull(player, "player");
        // Access to the current/parent object
        this.target = Objects.requireNonNull(target, "target");
    // End of a block/expression
    }

    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return target;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
