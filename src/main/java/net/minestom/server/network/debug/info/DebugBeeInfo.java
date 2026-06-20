// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record DebugBeeInfo(
        // Annotation for the following element
        @Nullable Point hivePosition,
        // Annotation for the following element
        @Nullable Point flowerPosition,
        // Code statement
        int travelTicks,
        // Code statement
        List<Point> blacklistedHives
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugBeeInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBeeInfo::hivePosition,
            // Code statement
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBeeInfo::flowerPosition,
            // Code statement
            NetworkBuffer.VAR_INT, DebugBeeInfo::travelTicks,
            // Code statement
            NetworkBuffer.BLOCK_POSITION.list(), DebugBeeInfo::blacklistedHives,
            // Code statement
            DebugBeeInfo::new);

    // Start of a method/block
    public DebugBeeInfo {
        // Calls a method
        blacklistedHives = List.copyOf(blacklistedHives);
    // End of a block/expression
    }
// End of a block/expression
}
