// Package declaration for this file
package net.minestom.server.network.packet.server;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.lang.ref.SoftReference;
// Import of a required class
import java.util.function.Supplier;

/**
 * Represents a packet that is only computed when required (either due to memory demand or invalidated data)
 * <p>
 * The cache is stored in a {@link SoftReference} and is invalidated when {@link #invalidate()} is called.
 * <p>
 * Packet supplier must be thread-safe.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class CachedPacket implements SendablePacket {
    // Code statement
    private final Supplier<ServerPacket> packetSupplier;
    // Code statement
    private volatile @Nullable SoftReference<FramedPacket> packet;

    // Start of a method/block
    public CachedPacket(Supplier<ServerPacket> packetSupplier) {
        // Access to the current/parent object
        this.packetSupplier = packetSupplier;
    // End of a block/expression
    }

    // Start of a method/block
    public CachedPacket(ServerPacket packet) {
        // Calls a method
        this(() -> packet);
    // End of a block/expression
    }

    // Start of a method/block
    public void invalidate() {
        // Access to the current/parent object
        this.packet = null;
    // End of a block/expression
    }

    // Start of a method/block
    public ServerPacket packet(ConnectionState state) {
        // Calls a method
        FramedPacket cache = updatedCache(state);
        // Returns a value to the caller
        return cache != null ? cache.packet() : packetSupplier.get();
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable NetworkBuffer body(ConnectionState state) {
        // Calls a method
        FramedPacket cache = updatedCache(state);
        // Returns a value to the caller
        return cache != null ? cache.body() : null;
    // End of a block/expression
    }

    // Start of a method/block
    private @Nullable FramedPacket updatedCache(ConnectionState state) {
        // Branch: checks a condition
        if (!ServerFlag.CACHED_PACKET)
            // Returns a value to the caller
            return null;
        // Assigns a value
        SoftReference<FramedPacket> ref = packet;
        // Code statement
        FramedPacket cache;
        // Branch: checks a condition
        if (ref == null || (cache = ref.get()) == null) {
            // Calls a method
            final ServerPacket packet = packetSupplier.get();
            // Assigns a value
            final NetworkBuffer buffer = PacketWriting.allocateTrimmedPacket(state, packet,
                    // Calls a method
                    MinecraftServer.getCompressionThreshold());
            // Calls a method
            cache = new FramedPacket(packet, buffer);
            // Access to the current/parent object
            this.packet = new SoftReference<>(cache);
        // End of a block/expression
        }
        // Returns a value to the caller
        return cache;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isValid() {
        // Assigns a value
        final SoftReference<FramedPacket> ref = packet;
        // Returns a value to the caller
        return ref != null && ref.get() != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Assigns a value
        final SoftReference<FramedPacket> ref = packet;
        // Calls a method
        final FramedPacket cache = ref != null ? ref.get() : null;
        // Returns a value to the caller
        return String.format("CachedPacket{cache=%s}", cache);
    // End of a block/expression
    }
// End of a block/expression
}
