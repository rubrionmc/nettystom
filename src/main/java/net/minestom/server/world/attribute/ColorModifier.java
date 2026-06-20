// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.kyori.adventure.util.ARGBLike;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.color.AlphaColor;
// Import of a required class
import net.minestom.server.utils.Either;

// Type declaration (class/interface/enum/record)
non-sealed interface ColorModifier<Arg> extends EnvironmentAttribute.Modifier<RGBLike, Arg> {
    // Assigns a value
    Codec<RGBLike> MAYBE_ARGB_CODEC = Codec.Either(AlphaColor.ARGB_STRING_CODEC, net.minestom.server.color.Color.STRING_CODEC).transform(
            // Code statement
            either -> either.unify(c -> c, c -> c),
            // Calls a method
            color -> color instanceof ARGBLike argb && argb.alpha() != 255 ? Either.left(argb) : Either.right(color));

    // Assigns a value
    ColorModifier<ARGBLike> ALPHA_BLEND = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, ARGBLike argument) {
            // Throws an exception
            throw new UnsupportedOperationException("alpha blend is not implemented yet");
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<ARGBLike> argumentCodec() {
            // Returns a value to the caller
            return AlphaColor.ARGB_STRING_CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ColorModifier<RGBLike> ADD = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Returns a value to the caller
            return new AlphaColor(
                    // Code statement
                    subject instanceof ARGBLike argb ? argb.alpha() : 255,
                    // Code statement
                    Math.min(255, subject.red() + argument.red()),
                    // Code statement
                    Math.min(255, subject.green() + argument.green()),
                    // Code statement
                    Math.min(255, subject.blue() + argument.blue())
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<RGBLike> argumentCodec() {
            // Returns a value to the caller
            return MAYBE_ARGB_CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ColorModifier<RGBLike> SUBTRACT = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Returns a value to the caller
            return new AlphaColor(
                    // Code statement
                    subject instanceof ARGBLike argb ? argb.alpha() : 255,
                    // Code statement
                    Math.max(0, subject.red() - argument.red()),
                    // Code statement
                    Math.max(0, subject.green() - argument.green()),
                    // Code statement
                    Math.max(0, subject.blue() - argument.blue())
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<RGBLike> argumentCodec() {
            // Returns a value to the caller
            return MAYBE_ARGB_CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ColorModifier<RGBLike> MULTIPLY_RGB = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Calls a method
            int subAlpha = subject instanceof ARGBLike argb ? argb.alpha() : 255;
            // Calls a method
            int argAlpha = argument instanceof ARGBLike argb ? argb.alpha() : 255;
            // Returns a value to the caller
            return new AlphaColor(
                    // Code statement
                    (subAlpha * argAlpha) / 255,
                    // Code statement
                    (subject.red() * argument.red()) / 255,
                    // Code statement
                    (subject.green() * argument.green()) / 255,
                    // Code statement
                    (subject.blue() * argument.blue()) / 255
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<RGBLike> argumentCodec() {
            // Returns a value to the caller
            return net.minestom.server.color.Color.STRING_CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ColorModifier<ARGBLike> MULTIPLY_ARGB = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, ARGBLike argument) {
            // Calls a method
            int subAlpha = subject instanceof ARGBLike argb ? argb.alpha() : 255;
            // Calls a method
            int argAlpha = argument instanceof ARGBLike argb ? argb.alpha() : 255;
            // Returns a value to the caller
            return new AlphaColor(
                    // Code statement
                    (subAlpha * argAlpha) / 255,
                    // Code statement
                    (subject.red() * argument.red()) / 255,
                    // Code statement
                    (subject.green() * argument.green()) / 255,
                    // Code statement
                    (subject.blue() * argument.blue()) / 255
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<ARGBLike> argumentCodec() {
            // Returns a value to the caller
            return AlphaColor.RGBA_STRING_CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };
    // Assigns a value
    ColorModifier<BlendToGray> BLEND_TO_GRAY = new ColorModifier<>() {
        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public RGBLike modify(RGBLike subject, BlendToGray argument) {
            // Throws an exception
            throw new UnsupportedOperationException("blend to gray is not implemented yet");
        // End of a block/expression
        }

        // Annotation for the following element
        @java.lang.Override
        // Start of a method/block
        public Codec<BlendToGray> argumentCodec() {
            // Returns a value to the caller
            return BlendToGray.CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    };

// End of a block/expression
}
