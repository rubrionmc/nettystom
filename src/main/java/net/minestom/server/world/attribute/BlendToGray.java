// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Type declaration (class/interface/enum/record)
public record BlendToGray(float brightness, float factor) {
    // Assigns a value
    public static final Codec<BlendToGray> CODEC = StructCodec.struct(
            // Code statement
            "brightness", Codec.FLOAT, BlendToGray::brightness,
            // Code statement
            "factor", Codec.FLOAT, BlendToGray::factor,
            // Code statement
            BlendToGray::new);
// End of a block/expression
}
