// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.crypto.MessageSignature;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record DeleteChatPacket(MessageSignature signature) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DeleteChatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            MessageSignature.SERIALIZER, DeleteChatPacket::signature,
            // Code statement
            DeleteChatPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
