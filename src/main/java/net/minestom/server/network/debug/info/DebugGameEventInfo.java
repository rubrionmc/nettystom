// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.game.GameEvent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record DebugGameEventInfo(GameEvent event, Point position) {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugGameEventInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            GameEvent.NETWORK_TYPE, DebugGameEventInfo::event,
            // Code statement
            NetworkBuffer.VECTOR3, DebugGameEventInfo::position,
            // Code statement
            DebugGameEventInfo::new);
// End of a block/expression
}
