// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class PlayerGameRulesRequestEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;

    // Start of a method/block
    public PlayerGameRulesRequestEvent(Player player) {
        // Access to the current/parent object
        this.player = Objects.requireNonNull(player, "player");
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
