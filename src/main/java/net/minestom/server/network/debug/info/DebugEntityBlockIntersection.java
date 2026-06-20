// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public enum DebugEntityBlockIntersection {
    // Code statement
    IN_BLOCK,
    // Code statement
    IN_FLUID,
    // Code statement
    IN_AIR;

    // Calls a method
    public static final NetworkBuffer.Type<DebugEntityBlockIntersection> SERIALIZER = NetworkBuffer.Enum(DebugEntityBlockIntersection.class);
// End of a block/expression
}
