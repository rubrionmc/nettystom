// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.time.Instant;

// Déclaration de type (classe/interface/enum/record)
public final class SignedMessageBody {

    // Déclaration de type (classe/interface/enum/record)
    public record Packed(String content, Instant timeStamp, long salt,
                         // Début d'une méthode/d'un bloc
                         LastSeenMessages.Packed lastSeen) {
        // Début d'une méthode/d'un bloc
        public Packed {
            // Embranchement : vérifie une condition
            if (content.length() > MessageSignature.SIGNATURE_BYTE_LENGTH) {
                // Lève une exception
                throw new IllegalArgumentException("Message content too long");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<Packed> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING, Packed::content,
                // Instruction de code
                NetworkBuffer.INSTANT_MS, Packed::timeStamp,
                // Instruction de code
                NetworkBuffer.LONG, Packed::salt,
                // Instruction de code
                LastSeenMessages.Packed.SERIALIZER, Packed::lastSeen,
                // Instruction de code
                Packed::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
