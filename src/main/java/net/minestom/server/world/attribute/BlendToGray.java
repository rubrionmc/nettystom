// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Déclaration de type (classe/interface/enum/record)
public record BlendToGray(float brightness, float factor) {
    // Affecte une valeur
    public static final Codec<BlendToGray> CODEC = StructCodec.struct(
            // Instruction de code
            "brightness", Codec.FLOAT, BlendToGray::brightness,
            // Instruction de code
            "factor", Codec.FLOAT, BlendToGray::factor,
            // Instruction de code
            BlendToGray::new);
// Fin d'un bloc/d'une expression
}
