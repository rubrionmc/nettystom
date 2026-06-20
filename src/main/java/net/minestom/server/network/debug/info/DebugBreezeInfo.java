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

// Type declaration (class/interface/enum/record)
public record DebugBreezeInfo(
        // Annotation for the following element
        @Nullable Integer attackTarget,
        // Annotation for the following element
        @Nullable Point jumpTarget
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugBreezeInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT.optional(), DebugBreezeInfo::attackTarget,
            // Code statement
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBreezeInfo::jumpTarget,
            // Code statement
            DebugBreezeInfo::new);
// End of a block/expression
}
