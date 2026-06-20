// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record DebugGameEventListenerInfo(int listenerRadius) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugGameEventListenerInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, DebugGameEventListenerInfo::listenerRadius,
            // Code statement
            DebugGameEventListenerInfo::new);
// End of a block/expression
}
