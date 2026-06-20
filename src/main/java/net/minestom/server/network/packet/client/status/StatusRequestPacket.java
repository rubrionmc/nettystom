// Package declaration for this file
package net.minestom.server.network.packet.client.status;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record StatusRequestPacket() implements ClientPacket.Status {
    // Calls a method
    public static final NetworkBuffer.Type<StatusRequestPacket> SERIALIZER = NetworkBufferTemplate.template(new StatusRequestPacket());
// End of a block/expression
}
