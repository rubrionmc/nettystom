// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientTickEndPacket() implements ClientPacket.Play {
    // Code statement
    public static final NetworkBuffer.Type<ClientTickEndPacket> SERIALIZER =
            // Calls a method
            NetworkBufferTemplate.template(new ClientTickEndPacket());

// End of a block/expression
}
