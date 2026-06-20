// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record ChunkBiomesPacket(List<ChunkBiomeData> chunks) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ChunkBiomesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            ChunkBiomeData.SERIALIZER.list(), ChunkBiomesPacket::chunks,
            // Code statement
            ChunkBiomesPacket::new);

    // Start of a method/block
    public ChunkBiomesPacket {
        // Assigns a value
        chunks = List.copyOf(chunks); // TODO deep copy?
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record ChunkBiomeData(int chunkX, int chunkZ, byte[] data) {
        // x and z are inverted, not a bug
        // Assigns a value
        public static final NetworkBuffer.Type<ChunkBiomeData> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                INT, ChunkBiomeData::chunkZ,
                // Code statement
                INT, ChunkBiomeData::chunkX,
                // Code statement
                BYTE_ARRAY, ChunkBiomeData::data,
                // Code statement
                (z, x, data) -> new ChunkBiomeData(x, z, data)
        // End of a block/expression
        );

        // Start of a method/block
        public ChunkBiomeData {
            // Calls a method
            data = data.clone();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean equals(Object o) {
            // Branch: checks a condition
            if (!(o instanceof ChunkBiomeData(int x, int z, byte[] data1))) return false;
            // Returns a value to the caller
            return chunkX() == x && chunkZ() == z && Arrays.equals(data(), data1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int hashCode() {
            // Calls a method
            int result = chunkX();
            // Calls a method
            result = 31 * result + chunkZ();
            // Calls a method
            result = 31 * result + Arrays.hashCode(data());
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
