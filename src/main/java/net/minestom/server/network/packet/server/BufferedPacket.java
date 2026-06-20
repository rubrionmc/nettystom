// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a buffer to directly write to the network.
 * <p>
 * May contain multiple packets.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public record BufferedPacket(NetworkBuffer buffer,
                             // Start of a method/block
                             long index, long length) implements SendablePacket {
    // Start of a method/block
    public BufferedPacket {
        // Calls a method
        buffer.readOnly();
    // End of a block/expression
    }
// End of a block/expression
}
