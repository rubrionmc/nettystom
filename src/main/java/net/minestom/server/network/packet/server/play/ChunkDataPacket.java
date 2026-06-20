// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.LightData;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record ChunkDataPacket(
        // Code statement
        int chunkX, int chunkZ,
        // Code statement
        ChunkData chunkData,
        // Code statement
        LightData lightData
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ChunkDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, ChunkDataPacket::chunkX,
            // Code statement
            INT, ChunkDataPacket::chunkZ,
            // Code statement
            ChunkData.NETWORK_TYPE, ChunkDataPacket::chunkData,
            // Code statement
            LightData.NETWORK_TYPE, ChunkDataPacket::lightData,
            // Code statement
            ChunkDataPacket::new
    // End of a block/expression
    );
// End of a block/expression
}