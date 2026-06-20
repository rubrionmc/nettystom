// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record DebugChunkValuePacket(long chunkPos, DebugSubscription.Update<?> update) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugChunkValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.LONG, DebugChunkValuePacket::chunkPos,
            // Code statement
            DebugSubscription.Update.NETWORK_TYPE, DebugChunkValuePacket::update,
            // Code statement
            DebugChunkValuePacket::new);
// End of a block/expression
}
