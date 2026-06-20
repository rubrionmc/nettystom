// Package declaration for this file
package net.minestom.server.network.packet.server.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record SetCompressionPacket(int threshold) implements ServerPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<SetCompressionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, SetCompressionPacket::threshold,
            // Code statement
            SetCompressionPacket::new);
// End of a block/expression
}
