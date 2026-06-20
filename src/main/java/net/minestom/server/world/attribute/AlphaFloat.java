// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.utils.Either;

// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public record AlphaFloat(float value, float alpha) {
    // Assigns a value
    private static final StructCodec<AlphaFloat> STRUCT_CODEC = StructCodec.struct(
            // Code statement
            "value", Codec.FLOAT, AlphaFloat::value,
            // Code statement
            "alpha", Codec.FLOAT.optional(1f), AlphaFloat::alpha,
            // Code statement
            AlphaFloat::new);
    // Assigns a value
    public static final Codec<AlphaFloat> CODEC = Codec.Either(Codec.FLOAT, STRUCT_CODEC).transform(
            // Code statement
            either -> either.unify(AlphaFloat::new, Function.identity()),
            // Code statement
            alphaFloat -> alphaFloat.alpha() == 1f ? Either.left(alphaFloat.value()) : Either.right(alphaFloat)
    // End of a block/expression
    );

    // Start of a method/block
    public AlphaFloat(float value) {
        // Calls a method
        this(value, 1f);
    // End of a block/expression
    }
// End of a block/expression
}
