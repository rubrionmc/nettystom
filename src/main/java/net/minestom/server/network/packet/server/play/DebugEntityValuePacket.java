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
public record DebugEntityValuePacket(int entityId, DebugSubscription.Update<?> update) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugEntityValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT, DebugEntityValuePacket::entityId,
            // Code statement
            DebugSubscription.Update.NETWORK_TYPE, DebugEntityValuePacket::update,
            // Code statement
            DebugEntityValuePacket::new);
// End of a block/expression
}
