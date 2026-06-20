// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.BitSet;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BITSET;

// Déclaration de type (classe/interface/enum/record)
public record FilterMask(Type type, BitSet mask) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<FilterMask> SERIALIZER = NetworkBuffer.Tagged(
            // Instruction de code
            NetworkBuffer.Enum(Type.class), FilterMask::type,
            // Instruction de code
            Map.of(
                    // Instruction de code
                    Type.PASS_THROUGH, NetworkBufferTemplate.template(new FilterMask(Type.PASS_THROUGH, new BitSet())),
                    // Instruction de code
                    Type.FULLY_FILTERED, NetworkBufferTemplate.template(new FilterMask(Type.FULLY_FILTERED, new BitSet())),
                    // Instruction de code
                    Type.PARTIALLY_FILTERED, NetworkBufferTemplate.template(
                            // Instruction de code
                            BITSET, FilterMask::mask,
                            // Instruction de code
                            mask -> new FilterMask(Type.PARTIALLY_FILTERED, mask))
            // Fin d'un bloc/d'une expression
            )
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public FilterMask {
        // Appelle une méthode
        mask = (BitSet) mask.clone();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        PASS_THROUGH,
        // Instruction de code
        FULLY_FILTERED,
        // Instruction de code
        PARTIALLY_FILTERED
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
