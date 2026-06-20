// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientPlayerLoadedPacket() implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlayerLoadedPacket> SERIALIZER = NetworkBufferTemplate
            // Calls a method
            .template(new ClientPlayerLoadedPacket());
// End of a block/expression
}
