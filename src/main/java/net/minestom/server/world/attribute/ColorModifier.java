// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.kyori.adventure.util.ARGBLike;
// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.color.AlphaColor;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;

// Déclaration de type (classe/interface/enum/record)
non-sealed interface ColorModifier<Arg> extends EnvironmentAttribute.Modifier<RGBLike, Arg> {
    // Affecte une valeur
    Codec<RGBLike> MAYBE_ARGB_CODEC = Codec.Either(AlphaColor.ARGB_STRING_CODEC, net.minestom.server.color.Color.STRING_CODEC).transform(
            // Instruction de code
            either -> either.unify(c -> c, c -> c),
            // Appelle une méthode
            color -> color instanceof ARGBLike argb && argb.alpha() != 255 ? Either.left(argb) : Either.right(color));

    // Affecte une valeur
    ColorModifier<ARGBLike> ALPHA_BLEND = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, ARGBLike argument) {
            // Lève une exception
            throw new UnsupportedOperationException("alpha blend is not implemented yet");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<ARGBLike> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return AlphaColor.ARGB_STRING_CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ColorModifier<RGBLike> ADD = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Renvoie une valeur à l'appelant
            return new AlphaColor(
                    // Instruction de code
                    subject instanceof ARGBLike argb ? argb.alpha() : 255,
                    // Instruction de code
                    Math.min(255, subject.red() + argument.red()),
                    // Instruction de code
                    Math.min(255, subject.green() + argument.green()),
                    // Instruction de code
                    Math.min(255, subject.blue() + argument.blue())
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<RGBLike> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return MAYBE_ARGB_CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ColorModifier<RGBLike> SUBTRACT = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Renvoie une valeur à l'appelant
            return new AlphaColor(
                    // Instruction de code
                    subject instanceof ARGBLike argb ? argb.alpha() : 255,
                    // Instruction de code
                    Math.max(0, subject.red() - argument.red()),
                    // Instruction de code
                    Math.max(0, subject.green() - argument.green()),
                    // Instruction de code
                    Math.max(0, subject.blue() - argument.blue())
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<RGBLike> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return MAYBE_ARGB_CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ColorModifier<RGBLike> MULTIPLY_RGB = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, RGBLike argument) {
            // Appelle une méthode
            int subAlpha = subject instanceof ARGBLike argb ? argb.alpha() : 255;
            // Appelle une méthode
            int argAlpha = argument instanceof ARGBLike argb ? argb.alpha() : 255;
            // Renvoie une valeur à l'appelant
            return new AlphaColor(
                    // Instruction de code
                    (subAlpha * argAlpha) / 255,
                    // Instruction de code
                    (subject.red() * argument.red()) / 255,
                    // Instruction de code
                    (subject.green() * argument.green()) / 255,
                    // Instruction de code
                    (subject.blue() * argument.blue()) / 255
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<RGBLike> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return net.minestom.server.color.Color.STRING_CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ColorModifier<ARGBLike> MULTIPLY_ARGB = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, ARGBLike argument) {
            // Appelle une méthode
            int subAlpha = subject instanceof ARGBLike argb ? argb.alpha() : 255;
            // Appelle une méthode
            int argAlpha = argument instanceof ARGBLike argb ? argb.alpha() : 255;
            // Renvoie une valeur à l'appelant
            return new AlphaColor(
                    // Instruction de code
                    (subAlpha * argAlpha) / 255,
                    // Instruction de code
                    (subject.red() * argument.red()) / 255,
                    // Instruction de code
                    (subject.green() * argument.green()) / 255,
                    // Instruction de code
                    (subject.blue() * argument.blue()) / 255
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<ARGBLike> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return AlphaColor.RGBA_STRING_CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ColorModifier<BlendToGray> BLEND_TO_GRAY = new ColorModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public RGBLike modify(RGBLike subject, BlendToGray argument) {
            // Lève une exception
            throw new UnsupportedOperationException("blend to gray is not implemented yet");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<BlendToGray> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return BlendToGray.CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

// Fin d'un bloc/d'une expression
}
