// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Déclaration de type (classe/interface/enum/record)
non-sealed interface FloatModifier<Arg> extends EnvironmentAttribute.Modifier<Float, Arg> {
    // Affecte une valeur
    FloatModifier<AlphaFloat> ALPHA_BLEND = new FloatModifier<>() {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public java.lang.Float modify(java.lang.Float sub, AlphaFloat arg) {
            // Renvoie une valeur à l'appelant
            return sub + arg.alpha() * (arg.value() - sub);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        public Codec<AlphaFloat> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return AlphaFloat.CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
    // Affecte une valeur
    ToFloat ADD = java.lang.Float::sum;
    // Appelle une méthode
    ToFloat SUBTRACT = (x, y) -> x - y;
    // Appelle une méthode
    ToFloat MULTIPLY = (x, y) -> x * y;
    // Affecte une valeur
    ToFloat MINIMUM = Math::min;
    // Affecte une valeur
    ToFloat MAXIMUM = Math::max;

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface ToFloat extends FloatModifier<java.lang.Float> {
        // Annotation pour l'élément suivant
        @java.lang.Override
        // Début d'une méthode/d'un bloc
        default Codec<java.lang.Float> argumentCodec() {
            // Renvoie une valeur à l'appelant
            return Codec.FLOAT;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
