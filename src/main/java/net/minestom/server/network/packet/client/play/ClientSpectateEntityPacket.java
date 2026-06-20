// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

/**
 * The ClientSpectateEntityPacket is sent when the client clicks on an entity to spectate it.
 */
// Type declaration (class/interface/enum/record)
public record ClientSpectateEntityPacket(int targetId) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSpectateEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, ClientSpectateEntityPacket::targetId,
            // Code statement
            ClientSpectateEntityPacket::new);
// End of a block/expression
}
