// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.BitSet;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BITSET;

// Type declaration (class/interface/enum/record)
public record FilterMask(Type type, BitSet mask) {
    // Assigns a value
    public static final NetworkBuffer.Type<FilterMask> SERIALIZER = NetworkBuffer.Tagged(
            // Code statement
            NetworkBuffer.Enum(Type.class), FilterMask::type,
            // Code statement
            Map.of(
                    // Code statement
                    Type.PASS_THROUGH, NetworkBufferTemplate.template(new FilterMask(Type.PASS_THROUGH, new BitSet())),
                    // Code statement
                    Type.FULLY_FILTERED, NetworkBufferTemplate.template(new FilterMask(Type.FULLY_FILTERED, new BitSet())),
                    // Code statement
                    Type.PARTIALLY_FILTERED, NetworkBufferTemplate.template(
                            // Code statement
                            BITSET, FilterMask::mask,
                            // Code statement
                            mask -> new FilterMask(Type.PARTIALLY_FILTERED, mask))
            // End of a block/expression
            )
    // End of a block/expression
    );

    // Start of a method/block
    public FilterMask {
        // Calls a method
        mask = (BitSet) mask.clone();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        PASS_THROUGH,
        // Code statement
        FULLY_FILTERED,
        // Code statement
        PARTIALLY_FILTERED
    // End of a block/expression
    }
// End of a block/expression
}
