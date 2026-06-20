// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record DebugHiveInfo(
        // Code statement
        Block type,
        // Code statement
        int occupantCount,
        // Code statement
        int honeyLevel,
        // Code statement
        boolean sedated
// Start of a method/block
) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugHiveInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Block.ID_NETWORK_TYPE, DebugHiveInfo::type,
            // Code statement
            NetworkBuffer.INT, DebugHiveInfo::occupantCount,
            // Code statement
            NetworkBuffer.INT, DebugHiveInfo::honeyLevel,
            // Code statement
            NetworkBuffer.BOOLEAN, DebugHiveInfo::sedated,
            // Code statement
            DebugHiveInfo::new);
// End of a block/expression
}
