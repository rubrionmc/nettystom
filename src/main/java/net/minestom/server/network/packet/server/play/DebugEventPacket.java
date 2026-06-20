// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record DebugEventPacket(DebugSubscription.Event<?> event) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            DebugSubscription.Event.NETWORK_TYPE, DebugEventPacket::event,
            // Code statement
            DebugEventPacket::new);
// End of a block/expression
}
