// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public record SignedMessageHeader(@Nullable MessageSignature previousSignature, UUID sender) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SignedMessageHeader> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            MessageSignature.SERIALIZER.optional(), SignedMessageHeader::previousSignature,
            // Instruction de code
            NetworkBuffer.UUID, SignedMessageHeader::sender,
            // Instruction de code
            SignedMessageHeader::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
