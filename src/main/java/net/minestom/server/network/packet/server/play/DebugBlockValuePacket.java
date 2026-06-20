// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record DebugBlockValuePacket(
        // Code statement
        Point blockPosition,
        // Code statement
        DebugSubscription.Update<?> update
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugBlockValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, DebugBlockValuePacket::blockPosition,
            // Code statement
            DebugSubscription.Update.NETWORK_TYPE, DebugBlockValuePacket::update,
            // Code statement
            DebugBlockValuePacket::new);
// End of a block/expression
}
