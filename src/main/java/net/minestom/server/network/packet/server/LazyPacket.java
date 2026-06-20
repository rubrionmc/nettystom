// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.Supplier;

/**
 * Represents a packet that is lazily allocated. Potentially in a different thread.
 * <p>
 * Supplier must be thread-safe.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class LazyPacket implements SendablePacket {
    // Code statement
    private final Supplier<ServerPacket> packetSupplier;
    // Code statement
    private volatile @Nullable ServerPacket packet;

    // Start of a method/block
    public LazyPacket(Supplier<ServerPacket> packetSupplier) {
        // Access to the current/parent object
        this.packetSupplier = packetSupplier;
    // End of a block/expression
    }

    // Start of a method/block
    public ServerPacket packet() {
        // Assigns a value
        ServerPacket packet = this.packet;
        // Branch: checks a condition
        if (packet == null) {
            // Start of a method/block
            synchronized (this) {
                // Assigns a value
                packet = this.packet;
                // Branch: checks a condition
                if (packet == null) this.packet = packet = packetSupplier.get();
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return packet;
    // End of a block/expression
    }
// End of a block/expression
}
