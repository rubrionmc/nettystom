// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record UnloadChunkPacket(int chunkX, int chunkZ) implements ServerPacket.Play {
    // Client reads this as a single long in big endian, so we have to write it backwards
    // Assigns a value
    public static final NetworkBuffer.Type<UnloadChunkPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, UnloadChunkPacket::chunkZ,
            // Code statement
            INT, UnloadChunkPacket::chunkX,
            // Code statement
            (z, x) -> new UnloadChunkPacket(x, z)
    // End of a block/expression
    );
// End of a block/expression
}
