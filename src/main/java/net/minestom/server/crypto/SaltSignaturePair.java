// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record SaltSignaturePair(long salt, byte[] signature) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SaltSignaturePair> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.LONG, SaltSignaturePair::salt,
            // Instruction de code
            NetworkBuffer.BYTE_ARRAY, SaltSignaturePair::signature,
            // Instruction de code
            SaltSignaturePair::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
