// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public enum MapPostProcessing {
    // Code statement
    LOCK,
    // Code statement
    SCALE;

    // Calls a method
    public static final NetworkBuffer.Type<MapPostProcessing> NETWORK_TYPE = NetworkBuffer.Enum(MapPostProcessing.class);
// End of a block/expression
}
