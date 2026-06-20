// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record TransferPacket(
        // Code statement
        String host,
        // Code statement
        int port
// Start of a method/block
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<TransferPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, TransferPacket::host,
            // Code statement
            NetworkBuffer.VAR_INT, TransferPacket::port,
            // Code statement
            TransferPacket::new);
// End of a block/expression
}
