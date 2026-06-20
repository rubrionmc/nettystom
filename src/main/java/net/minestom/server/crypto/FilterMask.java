// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.BitSet;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BITSET;

// Déclaration de type (classe/interface/enum/record)
public record FilterMask(Type type, BitSet mask) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<FilterMask> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, FilterMask value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.Enum(Type.class), value.type);
            // Embranchement : vérifie une condition
            if (value.type == Type.PARTIALLY_FILTERED) {
                // Appelle une méthode
                buffer.write(BITSET, value.mask);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public FilterMask read(NetworkBuffer buffer) {
            // Appelle une méthode
            Type type = buffer.read(NetworkBuffer.Enum(Type.class));
            // Appelle une méthode
            BitSet mask = type == Type.PARTIALLY_FILTERED ? buffer.read(BITSET) : new BitSet();
            // Renvoie une valeur à l'appelant
            return new FilterMask(type, mask);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

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
