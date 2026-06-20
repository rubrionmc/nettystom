// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Represents a packet that can be sent to a {@link PlayerConnection}.
 */
// Type declaration (class/interface/enum/record)
public sealed interface SendablePacket
        // Start of a method/block
        permits BufferedPacket, CachedPacket, FramedPacket, LazyPacket, ServerPacket {

    // Start of a method/block
    static @Nullable ServerPacket extractServerPacket(ConnectionState state, SendablePacket packet) {
        // Returns a value to the caller
        return switch (packet) {
            // Multiple branching (switch/case)
            case ServerPacket serverPacket -> serverPacket;
            // Multiple branching (switch/case)
            case CachedPacket cachedPacket -> cachedPacket.packet(state);
            // Multiple branching (switch/case)
            case FramedPacket framedPacket -> framedPacket.packet();
            // Multiple branching (switch/case)
            case LazyPacket lazyPacket -> lazyPacket.packet();
            // Multiple branching (switch/case)
            case BufferedPacket bufferedPacket -> null;
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
