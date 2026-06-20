// Package declaration for this file
package net.minestom.server.network.packet.server.status;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ResponsePacket(String jsonResponse) implements ServerPacket.Status {
    // Assigns a value
    public static final NetworkBuffer.Type<ResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ResponsePacket::jsonResponse,
            // Code statement
            ResponsePacket::new);
// End of a block/expression
}
