// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a packet which is already framed. (packet id+payload) + optional compression
 * Can be used if you want to send the exact same buffer to multiple clients without processing it more than once.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public record FramedPacket(ServerPacket packet,
                           // Start of a method/block
                           NetworkBuffer body) implements SendablePacket {
    // Start of a method/block
    public FramedPacket {
        // Calls a method
        body.readIndex(0);
        // Calls a method
        body.readOnly();
    // End of a block/expression
    }
// End of a block/expression
}
