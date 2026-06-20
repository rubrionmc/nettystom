// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record Food(int nutrition, float saturationModifier, boolean canAlwaysEat) {

    // Assigns a value
    public static final NetworkBuffer.Type<Food> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, Food::nutrition,
            // Code statement
            FLOAT, Food::saturationModifier,
            // Code statement
            BOOLEAN, Food::canAlwaysEat,
            // Code statement
            Food::new);
    // Assigns a value
    public static final Codec<Food> CODEC = StructCodec.struct(
            // Code statement
            "nutrition", Codec.INT, Food::nutrition,
            // Code statement
            "saturation", Codec.FLOAT, Food::saturationModifier,
            // Code statement
            "can_always_eat", Codec.BOOLEAN.optional(false), Food::canAlwaysEat,
            // Code statement
            Food::new
    // End of a block/expression
    );

// End of a block/expression
}
