// Déclaration du paquet de ce fichier
package net.minestom.server.crypto;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.Arrays;

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

    // Début d'une méthode/d'un bloc
    public SaltSignaturePair {
        // Appelle une méthode
        signature = signature.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof SaltSignaturePair(long salt1, byte[] signature1))) return false;
        // Renvoie une valeur à l'appelant
        return salt() == salt1 && Arrays.equals(signature(), signature1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = Long.hashCode(salt());
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(signature());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
