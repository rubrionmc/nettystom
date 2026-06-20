// Package declaration for this file
package net.minestom.server.network.packet.server.play.data;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.BitSet;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BITSET;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;

// Type declaration (class/interface/enum/record)
public record LightData(
        // Code statement
        BitSet skyMask, BitSet blockMask,
        // Code statement
        BitSet emptySkyMask, BitSet emptyBlockMask,
        // Code statement
        List<byte[]> skyLight,
        // Code statement
        List<byte[]> blockLight
// Start of a method/block
) {
    // Start of a method/block
    public LightData {
        // Calls a method
        skyMask = (BitSet) skyMask.clone();
        // Calls a method
        blockMask = (BitSet) blockMask.clone();
        // Calls a method
        emptySkyMask = (BitSet) emptySkyMask.clone();
        // Calls a method
        emptyBlockMask = (BitSet) emptyBlockMask.clone();
        // Assigns a value
        skyLight = List.copyOf(skyLight); //TODO deep copy?
        // Assigns a value
        blockLight = List.copyOf(blockLight); //TODO deep copy?
    // End of a block/expression
    }

    // Assigns a value
    public static final int MAX_SECTIONS = 4096 / 16;

    // Assigns a value
    public static final NetworkBuffer.Type<LightData> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            BITSET, LightData::skyMask,
            // Code statement
            BITSET, LightData::blockMask,
            // Code statement
            BITSET, LightData::emptySkyMask,
            // Code statement
            BITSET, LightData::emptyBlockMask,
            // Code statement
            BYTE_ARRAY.list(MAX_SECTIONS), LightData::skyLight,
            // Code statement
            BYTE_ARRAY.list(MAX_SECTIONS), LightData::blockLight,
            // Code statement
            LightData::new
    // End of a block/expression
    );
// End of a block/expression
}
