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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.LONG;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_LONG_ARRAY;

// Type declaration (class/interface/enum/record)
public record MultiBlockChangePacket(long chunkSectionPosition, long[] blocks) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<MultiBlockChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG, MultiBlockChangePacket::chunkSectionPosition,
            // Code statement
            VAR_LONG_ARRAY, MultiBlockChangePacket::blocks,
            // Code statement
            MultiBlockChangePacket::new);

    // Start of a method/block
    public MultiBlockChangePacket {
        // Calls a method
        blocks = blocks.clone();
    // End of a block/expression
    }

    // Start of a method/block
    public MultiBlockChangePacket(int chunkX, int section, int chunkZ, long[] blocks) {
        // Calls a method
        this(((long) (chunkX & 0x3FFFFF) << 42) | (section & 0xFFFFF) | ((long) (chunkZ & 0x3FFFFF) << 20), blocks);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof MultiBlockChangePacket(long sectionPosition, long[] blocks1))) return false;
        // Returns a value to the caller
        return chunkSectionPosition() == sectionPosition && Arrays.equals(blocks(), blocks1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Long.hashCode(chunkSectionPosition());
        // Calls a method
        result = 31 * result + Arrays.hashCode(blocks());
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
