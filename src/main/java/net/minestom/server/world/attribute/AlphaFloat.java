// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;

// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public record AlphaFloat(float value, float alpha) {
    // Affecte une valeur
    private static final StructCodec<AlphaFloat> STRUCT_CODEC = StructCodec.struct(
            // Instruction de code
            "value", Codec.FLOAT, AlphaFloat::value,
            // Instruction de code
            "alpha", Codec.FLOAT.optional(1f), AlphaFloat::alpha,
            // Instruction de code
            AlphaFloat::new);
    // Affecte une valeur
    public static final Codec<AlphaFloat> CODEC = Codec.Either(Codec.FLOAT, STRUCT_CODEC).transform(
            // Instruction de code
            either -> either.unify(AlphaFloat::new, Function.identity()),
            // Instruction de code
            alphaFloat -> alphaFloat.alpha() == 1f ? Either.left(alphaFloat.value()) : Either.right(alphaFloat)
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public AlphaFloat(float value) {
        // Appelle une méthode
        this(value, 1f);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
