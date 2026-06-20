// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record LodestoneTracker(@Nullable WorldPos target, boolean tracked) {

    // Assigns a value
    public static final NetworkBuffer.Type<LodestoneTracker> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            WorldPos.NETWORK_TYPE.optional(), LodestoneTracker::target,
            // Code statement
            NetworkBuffer.BOOLEAN, LodestoneTracker::tracked,
            // Code statement
            LodestoneTracker::new);
    // Assigns a value
    public static final Codec<LodestoneTracker> CODEC = StructCodec.struct(
            // Code statement
            "target", WorldPos.CODEC.optional(), LodestoneTracker::target,
            // Code statement
            "tracked", Codec.BOOLEAN.optional(true), LodestoneTracker::tracked,
            // Code statement
            LodestoneTracker::new);

    // Start of a method/block
    public LodestoneTracker(String dimension, Point blockPosition, boolean tracked) {
        // Calls a method
        this(new WorldPos(dimension, blockPosition), tracked);
    // End of a block/expression
    }

    // Start of a method/block
    public LodestoneTracker withTarget(@Nullable WorldPos target) {
        // Returns a value to the caller
        return new LodestoneTracker(target, tracked);
    // End of a block/expression
    }

    // Start of a method/block
    public LodestoneTracker withTracked(boolean tracked) {
        // Returns a value to the caller
        return new LodestoneTracker(target, tracked);
    // End of a block/expression
    }

// End of a block/expression
}
