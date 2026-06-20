// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record FinishConfigurationPacket() implements ServerPacket.Configuration {
    // Calls a method
    public static final NetworkBuffer.Type<FinishConfigurationPacket> SERIALIZER = NetworkBufferTemplate.template(new FinishConfigurationPacket());
// End of a block/expression
}
