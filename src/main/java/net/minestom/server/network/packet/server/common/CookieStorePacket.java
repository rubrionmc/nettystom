// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.Arrays;

// Déclaration de type (classe/interface/enum/record)
public record CookieStorePacket(
        // Instruction de code
        String key, byte[] value
// Début d'une méthode/d'un bloc
) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_VALUE_LENGTH = 5120;

    // Affecte une valeur
    public static final NetworkBuffer.Type<CookieStorePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, CookieStorePacket::key,
            // Instruction de code
            NetworkBuffer.BYTE_ARRAY, CookieStorePacket::value,
            // Instruction de code
            CookieStorePacket::new);

    // Début d'une méthode/d'un bloc
    public CookieStorePacket {
        // Appelle une méthode
        Check.argCondition(value.length > MAX_VALUE_LENGTH, "Cookie value length too long: {0} > {1}", value.length, MAX_VALUE_LENGTH);
        // Appelle une méthode
        value = value.clone();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CookieStorePacket(Key key, byte[] value) {
        // Appelle une méthode
        this(key.asString(), value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof CookieStorePacket(String key1, byte[] value1))) return false;
        // Renvoie une valeur à l'appelant
        return key().equals(key1) && Arrays.equals(value(), value1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = key().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(value());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
