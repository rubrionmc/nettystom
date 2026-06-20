// Package declaration for this file
package net.minestom.server.network.packet.server.configuration;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record ResetChatPacket() implements ServerPacket.Configuration {
    // Calls a method
    public static final NetworkBuffer.Type<ResetChatPacket> SERIALIZER = NetworkBufferTemplate.template(new ResetChatPacket());
// End of a block/expression
}
