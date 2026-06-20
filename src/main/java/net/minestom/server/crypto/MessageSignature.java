// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record MessageSignature(byte [] signature) {
    // Affecte une valeur
    static final int SIGNATURE_BYTE_LENGTH = 256;

    // Début d'une méthode/d'un bloc
    public MessageSignature {
        // Embranchement : vérifie une condition
        if (signature.length != SIGNATURE_BYTE_LENGTH) {
            // Lève une exception
            throw new IllegalArgumentException("Signature must be 256 bytes long");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<MessageSignature> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.RAW_BYTES, MessageSignature::signature,
            // Instruction de code
            MessageSignature::new
    // Fin d'un bloc/d'une expression
    );

    // Déclaration de type (classe/interface/enum/record)
    public record Packed(int id, @UnknownNullability MessageSignature fullSignature) {
        // Début d'une méthode/d'un bloc
        private Packed(Packed packed) {
            // Appelle une méthode
            this(packed.id, packed.fullSignature);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<Packed> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Packed value) {
                // Appelle une méthode
                buffer.write(VAR_INT, value.id + 1);
                // Embranchement : vérifie une condition
                if (value.id == 0) buffer.write(MessageSignature.SERIALIZER, value.fullSignature);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Packed read(NetworkBuffer buffer) {
                // Appelle une méthode
                final int id = buffer.read(VAR_INT) - 1;
                // Renvoie une valeur à l'appelant
                return new Packed(id, id == -1 ? buffer.read(MessageSignature.SERIALIZER) : null);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
