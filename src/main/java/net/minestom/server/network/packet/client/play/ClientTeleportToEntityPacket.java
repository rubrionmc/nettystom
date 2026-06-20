// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record ClientTeleportToEntityPacket(UUID target) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientTeleportToEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.UUID, ClientTeleportToEntityPacket::target,
            // Code statement
            ClientTeleportToEntityPacket::new
    // End of a block/expression
    );

    // Start of a method/block
    public ClientTeleportToEntityPacket {
        // Calls a method
        Objects.requireNonNull(target, "target");
    // End of a block/expression
    }
// End of a block/expression
}
