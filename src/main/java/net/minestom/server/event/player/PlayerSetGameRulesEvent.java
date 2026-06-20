// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
public class PlayerSetGameRulesEvent implements PlayerInstanceEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final List<ClientSetGameRulesPacket.Entry> requestedRules;

    // Start of a method/block
    public PlayerSetGameRulesEvent(Player player, List<ClientSetGameRulesPacket.Entry> requestedRules) {
        // Access to the current/parent object
        this.player = Objects.requireNonNull(player, "player");
        // Calls a method
        Objects.requireNonNull(requestedRules, "requestedRules");
        // Access to the current/parent object
        this.requestedRules = List.copyOf(requestedRules);
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

    /// The requested new rules by the client.
    // Start of a method/block
    public List<ClientSetGameRulesPacket.Entry> getRequestedRules() {
        // Returns a value to the caller
        return requestedRules;
    // End of a block/expression
    }
// End of a block/expression
}
