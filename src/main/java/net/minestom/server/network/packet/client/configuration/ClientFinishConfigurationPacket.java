// Package declaration for this file
package net.minestom.server.network.packet.client.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientFinishConfigurationPacket() implements ClientPacket.Configuration {
    // Calls a method
    public static final NetworkBuffer.Type<ClientFinishConfigurationPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientFinishConfigurationPacket());
// End of a block/expression
}
