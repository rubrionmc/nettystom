// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientConfigurationAckPacket() implements ClientPacket.Play {
    // Calls a method
    public static final NetworkBuffer.Type<ClientConfigurationAckPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientConfigurationAckPacket());
// End of a block/expression
}
