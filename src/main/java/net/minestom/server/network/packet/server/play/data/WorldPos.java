// Package declaration for this file
package net.minestom.server.network.packet.server.play.data;

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

// Type declaration (class/interface/enum/record)
public record WorldPos(String dimension, Point blockPosition) {
    // Assigns a value
    public static final NetworkBuffer.Type<WorldPos> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.STRING, WorldPos::dimension,
            // Code statement
            NetworkBuffer.BLOCK_POSITION, WorldPos::blockPosition,
            // Code statement
            WorldPos::new);
    // Assigns a value
    public static final Codec<WorldPos> CODEC = StructCodec.struct(
            // Code statement
            "dimension", Codec.STRING, WorldPos::dimension,
            // Code statement
            "pos", Codec.BLOCK_POSITION, WorldPos::blockPosition,
            // Code statement
            WorldPos::new
    // End of a block/expression
    );

    // Start of a method/block
    public WorldPos withDimension(String dimension) {
        // Returns a value to the caller
        return new WorldPos(dimension, blockPosition);
    // End of a block/expression
    }

    // Start of a method/block
    public WorldPos withBlockPosition(Point blockPosition) {
        // Returns a value to the caller
        return new WorldPos(dimension, blockPosition);
    // End of a block/expression
    }
// End of a block/expression
}