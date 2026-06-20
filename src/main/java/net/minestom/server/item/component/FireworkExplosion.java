// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Type declaration (class/interface/enum/record)
public record FireworkExplosion(
        // Code statement
        Shape shape,
        // Code statement
        List<RGBLike> colors,
        // Code statement
        List<RGBLike> fadeColors,
        // Code statement
        boolean hasTrail,
        // Code statement
        boolean hasTwinkle
// Start of a method/block
) {

    // Type declaration (class/interface/enum/record)
    public enum Shape {
        // Code statement
        SMALL_BALL,
        // Code statement
        LARGE_BALL,
        // Code statement
        STAR,
        // Code statement
        CREEPER,
        // Code statement
        BURST
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<FireworkExplosion> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(Shape.class), FireworkExplosion::shape,
            // Code statement
            Color.NETWORK_TYPE.list(Short.MAX_VALUE), FireworkExplosion::colors,
            // Code statement
            Color.NETWORK_TYPE.list(Short.MAX_VALUE), FireworkExplosion::fadeColors,
            // Code statement
            BOOLEAN, FireworkExplosion::hasTrail,
            // Code statement
            BOOLEAN, FireworkExplosion::hasTwinkle,
            // Code statement
            FireworkExplosion::new);
    // Assigns a value
    public static final Codec<FireworkExplosion> CODEC = StructCodec.struct(
            // Code statement
            "shape", Codec.Enum(Shape.class), FireworkExplosion::shape,
            // Code statement
            "colors", Color.CODEC.list().optional(List.of()), FireworkExplosion::colors,
            // Code statement
            "fade_colors", Color.CODEC.list().optional(List.of()), FireworkExplosion::fadeColors,
            // Code statement
            "has_trail", Codec.BOOLEAN.optional(false), FireworkExplosion::hasTrail,
            // Code statement
            "has_twinkle", Codec.BOOLEAN.optional(false), FireworkExplosion::hasTwinkle,
            // Code statement
            FireworkExplosion::new);

    // Start of a method/block
    public FireworkExplosion {
        // Calls a method
        colors = List.copyOf(colors);
        // Calls a method
        fadeColors = List.copyOf(fadeColors);
    // End of a block/expression
    }
// End of a block/expression
}
