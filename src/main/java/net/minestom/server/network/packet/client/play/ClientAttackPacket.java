// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientAttackPacket(int targetId) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientAttackPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, ClientAttackPacket::targetId,
            // Code statement
            ClientAttackPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
