// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;

// Type declaration (class/interface/enum/record)
non-sealed interface FloatModifier<Arg> extends EnvironmentAttribute.Modifier<Float, Arg> {
    // Assigns a value
    FloatModifier<AlphaFloat> ALPHA_BLEND = new FloatModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public java.lang.Float modify(java.lang.Float sub, AlphaFloat arg) {
            // Returns a value to the caller
            return sub + arg.alpha() * (arg.value() - sub);
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<AlphaFloat> argumentCodec() {
            // Returns a value to the caller
            return AlphaFloat.CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ToFloat ADD = java.lang.Float::sum;
    // Calls a method
    ToFloat SUBTRACT = (x, y) -> x - y;
    // Calls a method
    ToFloat MULTIPLY = (x, y) -> x * y;
    // Assigns a value
    ToFloat MINIMUM = Math::min;
    // Assigns a value
    ToFloat MAXIMUM = Math::max;

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface ToFloat extends FloatModifier<java.lang.Float> {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        default Codec<java.lang.Float> argumentCodec() {
            // Returns a value to the caller
            return Codec.FLOAT;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
