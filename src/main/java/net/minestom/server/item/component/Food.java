// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record Food(int nutrition, float saturationModifier, boolean canAlwaysEat) {

    // Affecte une valeur
    public static final NetworkBuffer.Type<Food> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, Food::nutrition,
            // Instruction de code
            FLOAT, Food::saturationModifier,
            // Instruction de code
            BOOLEAN, Food::canAlwaysEat,
            // Instruction de code
            Food::new);
    // Affecte une valeur
    public static final Codec<Food> CODEC = StructCodec.struct(
            // Instruction de code
            "nutrition", Codec.INT, Food::nutrition,
            // Instruction de code
            "saturation", Codec.FLOAT, Food::saturationModifier,
            // Instruction de code
            "can_always_eat", Codec.BOOLEAN.optional(false), Food::canAlwaysEat,
            // Instruction de code
            Food::new
    // Fin d'un bloc/d'une expression
    );

// Fin d'un bloc/d'une expression
}
