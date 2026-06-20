// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientStatusPacket(Action action) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(Action.class), ClientStatusPacket::action,
            // Code statement
            ClientStatusPacket::new);

    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Code statement
        PERFORM_RESPAWN,
        // Code statement
        REQUEST_STATS,
        // Code statement
        REQUEST_GAMERULE_VALUES
    // End of a block/expression
    }
// End of a block/expression
}
