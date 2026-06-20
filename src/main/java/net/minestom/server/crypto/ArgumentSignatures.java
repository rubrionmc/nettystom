// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ArgumentSignatures(List<Entry> entries) {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 8;

    // Début d'une méthode/d'un bloc
    public ArgumentSignatures {
        // Appelle une méthode
        entries = List.copyOf(entries);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<ArgumentSignatures> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Entry.SERIALIZER.list(MAX_ENTRIES), ArgumentSignatures::entries,
            // Instruction de code
            ArgumentSignatures::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public record Entry(String name, MessageSignature signature) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Entry::name,
                // Instruction de code
                MessageSignature.SERIALIZER, Entry::signature,
                // Instruction de code
                Entry::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
