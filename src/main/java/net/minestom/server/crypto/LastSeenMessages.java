// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.BitSet;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FixedBitSet;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record LastSeenMessages(List<MessageSignature> entries) {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 20;

    // Début d'une méthode/d'un bloc
    public LastSeenMessages {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<LastSeenMessages> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            MessageSignature.SERIALIZER.list(MAX_ENTRIES), LastSeenMessages::entries,
            // Instruction de code
            LastSeenMessages::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public record Packed(List<MessageSignature.Packed> entries) {
        // Appelle une méthode
        public static final Packed EMPTY = new Packed(List.of());

        // Affecte une valeur
        public static final NetworkBuffer.Type<Packed> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                MessageSignature.Packed.SERIALIZER.list(MAX_ENTRIES), Packed::entries,
                // Instruction de code
                Packed::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Packed {
            // Appelle une méthode
            entries = List.copyOf(entries);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Update(int offset, BitSet acknowledged) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Update> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_INT, Update::offset,
                // Instruction de code
                FixedBitSet(20), Update::acknowledged,
                // Instruction de code
                Update::new
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public Update {
            // Appelle une méthode
            acknowledged = (BitSet) acknowledged.clone();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
